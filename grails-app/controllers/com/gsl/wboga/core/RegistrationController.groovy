package com.gsl.wboga.core

import com.gsl.wboga.uma.security.Role
import com.gsl.wboga.uma.security.User
import com.gsl.wboga.uma.security.UserRole

class RegistrationController {

    def simpleCaptchaService

    def create(){
        Random r = new Random();
        Long hintId = r.nextInt(Hint.count() - 0 + 1) + 0 as Long;
        Hint hint = Hint.get(hintId)
        render(view: '_showRegistration', model: [hint:hint])
    }


    def save() {
        if (!request.method == 'POST') {
            flash.message = "Registration Error."
            redirect(action: 'create')
            return
        }
        boolean captchaValid = simpleCaptchaService.validateCaptcha(params.captcha)
        if (!captchaValid) {
            flash.message = "Captcha did not match! Try Again!!"
            redirect(action: 'create')
            return
        }

        // Save User
        User user = new User(
                username: params.username,
                email: params.email,
                password: params.password,
                enabled: true
        )

        User savedUser = user.save(flush: true)
        if (!savedUser){
            flash.message = "Username & Email must be unique!"
            redirect(action: 'create')
            return
        }
        Role role = Role.read(3)
        UserRole userRole = new UserRole(user: user, role: role)
        userRole.save(flush: true)

        // Save Registration
        params.dob = Date.parse('dd/MM/yyyy', params.dob)
        Registration registration = new Registration(
                name: params.name,
                dob: params.dob,
                user: savedUser,
                answer: params.answer,
                country: params.country,
                hint: params.hint ,
                memberType: params.memberType
        )
        if (!registration.validate()) {
            flash.message = "Have some validated problem!"
            redirect(action: 'create')
            return
        }
        Registration savedRegistration = registration.save(flush: true)
        if (!savedRegistration) {
            flash.message = "Registration failed."
            redirect(action: 'create')
            return
        }
        flash.message = "Registration success. Please login here"
        redirect(controller: 'login', action: 'auth')
    }


    def checkEmail() {
        def email = params.email
        def checkEmail = Registration.findByEmail(email)
        if (checkEmail) {
            return true
        } else {
            return false
        }
    }

}
