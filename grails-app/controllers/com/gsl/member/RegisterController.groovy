package com.gsl.member

import grails.plugin.springsecurity.annotation.Secured

@Secured(['permitAll'])
class RegisterController {

    def index() {
        render(view: 'register')
    }
}
