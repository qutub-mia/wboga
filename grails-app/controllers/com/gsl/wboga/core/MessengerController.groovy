package com.gsl.wboga.core

import grails.plugin.springsecurity.annotation.Secured

class MessengerController {

    def index() {}

    @Secured(['ROLE_SUPER_ADMIN'])
    def inbox(){
        render(view: 'inbox')
    }

    @Secured(['ROLE_SUPER_ADMIN'])
    def send(){

    }

    @Secured(['ROLE_SUPER_ADMIN'])
    def compose(){

    }

    @Secured(['ROLE_SUPER_ADMIN'])
    def trash(){

    }

    @Secured(['ROLE_SUPER_ADMIN'])
    def chat(){

    }
}
