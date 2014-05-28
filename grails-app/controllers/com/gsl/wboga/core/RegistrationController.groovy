package com.gsl.wboga.core

import com.gsl.wboga.uma.security.Role
import com.gsl.wboga.uma.security.User
import com.gsl.wboga.uma.security.UserRole
import wboga.core.Hint
import wboga.core.Registration

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
        //params.dob = new Date(params.dob)
        params.dob = Date.parse('dd/MM/yyyy', params.dob)
        Registration registration = new Registration(params)
        if (!registration.validate()) {
            flash.message = "Username & Email must be unique!"
            redirect(action: 'create')
            return
        }
        Registration savedRegistration = registration.save(flush: true)
        if (!savedRegistration) {
            flash.message = "Registration failed."
            redirect(action: 'create')
            return
        }
        User user = new User(
                username: savedRegistration.username,
                email: savedRegistration.email,
                password: savedRegistration.password,
                enabled: true
        )
        user.save(flush: true)
        Role role = Role.read(3)
        UserRole userRole = new UserRole(user: user, role: role)
        userRole.save(flush: true)

        flash.message = "Registration success"
        redirect([controller: 'login', action: 'auth'])
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
