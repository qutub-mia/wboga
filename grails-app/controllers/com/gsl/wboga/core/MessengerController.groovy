package com.gsl.wboga.core

import com.gsl.wboga.uma.security.User
import grails.converters.JSON


class MessengerController {

    def inbox(){
        User user = getAuthenticatedUser()
        render(view: 'messageInbox', model: [userId : user.id])
    }


    def send(){
        User user = getAuthenticatedUser()
        render(view: 'messageSend', model: [userId : user.id])
    }

    def compose(){
        render(view: '_compose')
    }

    def trash(){

    }

    def chat(){

    }

    def save(){
        User user = getAuthenticatedUser()
        Registration senderReg = Registration.findByUser(user)

        Messenger messenger
        for (def userId in params.username){
            def dateOfMessage = new Date()
            String subject = params.subject
            String messageBody= params.messageBody
            def receiver = userId
            def messengerId = params.messenger

            messenger = new Messenger(
                    subject: subject,
                    messageBody: messageBody,
                    sender: senderReg,
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
                render(view: 'messageSend')
                return
            }
        }

        flash.message = "Send Message Successfully!!"
        //redirect(controller: 'messenger', action: 'send')
        render(view: 'messageSend', model: [userId : user.id])
    }

    def view(){
        Messenger messengerList = Messenger.get(params.id as Long)

//        println ">>" + messengerList.messengers.receiver.id
        render(view: 'messageView', model: [messengerList: messengerList])
    }

    def reply(){
        println ">>" + params.parentId
        println ">>" + params.messageBody

        /**/
        User user = getAuthenticatedUser()
        Registration senderReg = Registration.findByUser(user)

        Messenger messenger = Messenger.read(params.parentId as Long)

        def subject = messenger.subject
        def dateOfMessage = new Date()
        String messageBody= params.messageBody
        def receiver = messenger.sender
        def messengerId = messenger

        messenger = new Messenger(
                subject: subject,
                messageBody: messageBody,
                sender: senderReg,
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
            render(view: 'messageSend')
            return
        }

        flash.message = "Reply Message Successfully!!"
        /**/


        Messenger messengerList = Messenger.read(params.parentId as Long)
        render(view: 'messageView', model: [messengerList: messengerList])
    }

    def sendList(){
        User user = getAuthenticatedUser()
        Registration senderReg = Registration.findByUser(user)

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

        Messenger messenger1 = new Messenger()



        def results = c.list (max: iDisplayLength, offset:iDisplayStart) {
            and {
                eq('sender',senderReg)
            }
            if(sSearch){
                or {
                    ilike('subject',sSearch)
                }
            }
            order(sortColumn, sSortDir)
        }

        println ">>" + results
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
                dataReturns.add([DT_RowId:messenger.id,0:serial,1:messenger.receiver.name,2:messenger.subject,3:messenger.dateOfMessage.format('dd/MM/yyyy'),4:''])
            }
        }
        Map gridData =[iTotalRecords:totalCount,iTotalDisplayRecords:totalCount,aaData:dataReturns]
        String result = gridData as JSON
        render result
    }

    def inboxList(){
        User user = getAuthenticatedUser()
        Registration receiverReg = Registration.findByUser(user)

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
                eq('receiver',receiverReg)
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
                dataReturns.add([DT_RowId:messenger.id,0:serial,1:messenger.sender.name,2:messenger.subject,3:messenger.dateOfMessage.format('dd/MM/yyyy'),4:''])
            }
        }
        Map gridData =[iTotalRecords:totalCount,iTotalDisplayRecords:totalCount,aaData:dataReturns]
        String result = gridData as JSON
        render result
    }
}
