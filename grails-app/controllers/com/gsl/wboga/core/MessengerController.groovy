package com.gsl.wboga.core

import grails.plugin.springsecurity.annotation.Secured
import wboga.core.Messenger
import wboga.core.Registration

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
        render(view: '_compose')
    }

    @Secured(['ROLE_SUPER_ADMIN'])
    def trash(){

    }

    @Secured(['ROLE_SUPER_ADMIN'])
    def chat(){

    }

    @Secured(['ROLE_SUPER_ADMIN'])
    def save(){
        println "main params >>" + params

        // Sender
        Registration senderRegistration = Registration.read(1 as Long)
        println "sender Username>"+ senderRegistration.username

        // Receiver
        def userList = params.username
        println ">>"+ userList

        /*params.dateOfMessage = Date.parse('dd/MM/yyyy', params.dateOfMessage)
        Messenger messenger = new Messenger(params)
        if (!messenger.validate()){
            flash.message = "Not added validated!"
            redirect(action: 'inbox')
            return
        }
        Messenger savedMessenger = messenger.save(flush: true)

        if (!savedMessenger){
            flash.message = "Not Added!"
            render(view: 'inbox')
            return
        }*/

    }
}
