package com.gsl.wboga.core

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import wboga.core.Messenger
import wboga.core.MessengerUtility
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

        // Default Sender
        Registration senderRegistration = Registration.read(2 as Long)

        Messenger messenger
        for (def userId in params.username){
            def dateOfMessage = new Date()
            String subject = params.subject
            String messageBody= params.messageBody
            String messagerFile = "wright.jpeg"
            def sender = senderRegistration
            def receiver = userId
            def messengerId = params.messenger

            messenger = new Messenger(
                    dateOfMessage: dateOfMessage,
                    subject: subject,
                    messageBody: messageBody,
                    showOn: false,
                    messagerFile: messagerFile,
                    sender: sender,
                    receiver: receiver,
                    messenger: messengerId
            )

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
            }
        }

        flash.message = "Send Message Successfully!!"
        redirect(action: 'inbox')
    }


    @Secured(['ROLE_SUPER_ADMIN'])
    def inboxList(){
        Registration sender = Registration.get(2) // default value for sender

        int sEcho = params.sEcho?params.getInt('sEcho'):1
        int iDisplayStart = params.iDisplayStart? params.getInt('iDisplayStart'):0
        int iDisplayLength = params.iDisplayLength? params.getInt('iDisplayLength'):10
        String sSortDir = params.sSortDir_0? params.sSortDir_0:'asc'
        int iSortingCol = params.iSortingCols? params.getInt('iSortingCols'):1
        //Search string, use or logic to all fields that required to include
        String sSearch = params.sSearch?params.sSearch:null
        if(sSearch){
            sSearch = "%"+sSearch+"%"
        }
        String sortColumn = MessengerUtility.getSortColumn(iSortingCol)
        List dataReturns = new ArrayList()
        def c = Messenger.createCriteria()
        def results = c.list (max: iDisplayLength, offset:iDisplayStart) {
            and {
                eq('sender',sender)
            }
            if(sSearch){
                or {
                    ilike('subject',sSearch)
                }
            }
            order(sortColumn, sSortDir)
        }

        int totalCount = results.totalCount
        int serial = iDisplayStart;
        if(totalCount>0){
            if(sSortDir.equalsIgnoreCase('desc')){
                serial =(totalCount+1)-iDisplayStart
            }
            results.each {Messenger messenger ->
                if(sSortDir.equalsIgnoreCase('asc')){
                    serial++
                }else{
                    serial--
                }
                dataReturns.add([DT_RowId:messenger.id,0:serial,1:messenger.receiver.name,2:messenger.subject,3:''])
            }
        }
        Map gridData =[iTotalRecords:totalCount,iTotalDisplayRecords:totalCount,aaData:dataReturns]
        String result = gridData as JSON
        render result
    }
}
