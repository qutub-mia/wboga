package com.gsl.wboga.member

import grails.plugin.springsecurity.annotation.Secured
import wboga.core.Hint
import wboga.core.Registration

@Secured(['permitAll'])
class RegisterController {

    def simpleCaptchaService

    def index() {}

    @Secured(['ROLE_SUPER_ADMIN'])
    def create() {
        Random r = new Random();
        Long hintId = r.nextInt(Hint.count() - 0 + 1) + 0 as Long;
        Hint hint = Hint.get(hintId)
        render(view: '/registration/_showRegistration', model: [hint: hint])
    }

    @Secured(['ROLE_SUPER_ADMIN'])
    def save() {
        if (!request.method == 'POST') {
            flash.message = "Registration data dose not added Successfully!"
            redirect(action: 'create')
            return
        }
        boolean captchaValid = simpleCaptchaService.validateCaptcha(params.captcha)
        if (!captchaValid) {
            flash.message = "Captcha problem! Try Again!!"
            redirect(action: 'create')
            return
        }
        //params.dob = new Date(params.dob)
        params.dob = Date.parse('dd/MM/yyyy', params.dob)

        Registration registration = new Registration(params)
        if (!registration.validate()) {
            flash.message = "Not added validated!"
            redirect(action: 'create')
            return
        }
        Registration savedRegistration = registration.save(flush: true)
        if (!savedRegistration) {
            flash.message = "Not added Successfully"
            redirect(action: 'create')
            return
        }
        flash.message = "Added Successfully !!"
        redirect(action: 'create')
    }

}
