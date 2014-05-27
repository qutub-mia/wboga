package com.gsl.wboga.core

import grails.plugin.springsecurity.annotation.Secured
import wboga.core.Hint
import wboga.core.Registration

class RegistrationController {

    def simpleCaptchaService
    def index() {}

    @Secured(['ROLE_ANONYMOUS'])
    def create(){
        Random r = new Random();
        Long hintId = r.nextInt(Hint.count() - 0 + 1) + 0 as Long;
        Hint hint = Hint.get(hintId)
        render(view: '_showRegistration', model: [hint:hint])
    }

    @Secured(['ROLE_ANONYMOUS'])
    def save(){
        if (!request.method == 'POST') {
            flash.message = "Registration data dose not added Successfully!"
            redirect(action: 'create')
            return
        }
        boolean captchaValid = simpleCaptchaService.validateCaptcha(params.captcha)
        if(!captchaValid){
            flash.message = "Captcha did not match! Try Again!!"
            redirect(action: 'create')
            return
        }
        //params.dob = new Date(params.dob)
        params.dob = Date.parse('dd/MM/yyyy', params.dob)
        Registration registration = new Registration(params)
        if (!registration.validate()){
            flash.message = "Not added validated! Username & Email must be unique!"
            redirect(action: 'create')
            return
        }
        Registration savedRegistration = registration.save(flush: true)
        if(!savedRegistration){
            flash.message = "Not added Successfully"
            redirect(action: 'create')
            return
        }
        flash.message = "Added Successfully !!"
        redirect(action: 'create')
    }

    @Secured(['ROLE_ANONYMOUS'])
    def checkEmail(){
        def email = params.email
        def checkEmail = Registration.findByEmail(email)
        if (checkEmail){
            return true
        }else {
            return false
        }
    }

}
