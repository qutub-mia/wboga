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
        User user = getAuthenticatedUser()
        render(view: 'messageTrash', model: [userId : user.id])
    }

    def trashDelete(){
        if (!request.method == 'POST') {
            flash.message = "Message not Delete!"
            redirect(action: 'inbox')
            return
        }
        Messenger messenger = Messenger.get(params.id as Long)
        if(!messenger){
            flash.message = "Message invalid!"
            redirect(action: 'trash')
            return
        }

        def subject = messenger.subject.trim()
        User user = getAuthenticatedUser()
        Registration registration = Registration.findByUser(user)

        def messengerSenderList = Messenger.findAllBySenderAndSubject(registration, subject)
        if(messengerSenderList != null){
            for (def messengerSender in messengerSenderList ){
                if(messengerSender.senderTrash == "isSenderOff"){
                    messengerSender.senderTrash = "delete"
                    messengerSender.save(flush: true)
                }
            }
        }

        def messengerReceiverList = Messenger.findAllByReceiverAndSubject(registration, subject)
        if(messengerReceiverList != null){
            for (def messengerReceiver in messengerReceiverList){
                if(messengerReceiver.receiverTrash == "isReceiverOff"){
                    messengerReceiver.receiverTrash = "delete"
                    messengerReceiver.save(flush: true)
                }
            }
        }

        if( (messenger.receiverTrash == "delete") && (messenger.senderTrash == "delete") ){
            def deleteMessage = messenger.delete()
            if(!deleteMessage){
                flash.message = "Message not delete in trash box!"
                redirect(action: 'trash')
                return
            }
            flash.message = "Message delete form trash box!"
            redirect(action: 'trash')
            return
        }

        flash.message = "Message delete form trash box!"
        redirect(action: 'trash')
    }

    def delete(){
        if (!request.method == 'POST') {
            flash.message = "Message not Delete!"
            redirect(action: 'inbox')
            return
        }
        String action = (params.view as String).trim()
        Messenger messenger = Messenger.get(params.id as Long)
        if(!messenger){
            flash.message = "Message invalid!"
            redirect(action: action)
            return
        }
        def subject = messenger.subject.trim()
        User user = getAuthenticatedUser()
        Registration registration = Registration.findByUser(user)

        if(action == "send"){
            def messengerSenderList = Messenger.findAllBySenderAndSubjectAndSenderTrash(registration,subject,"off")
            if(messengerSenderList != null){
                for (def messengerSender in messengerSenderList){
                    //if (messengerSender.senderTrash == "off"){
                        messengerSender.senderTrash = "isSenderOff"
                        messengerSender.save(flush: true)
                   // }
                }
            }
        }

        if (action == "inbox"){
            def messengerReceiverList = Messenger.findAllByReceiverAndSubjectAndReceiverTrash(registration,subject,"off")
            if(messengerReceiverList != null){
                for (def messengerReceiver in messengerReceiverList){
                    //if(messengerReceiver.receiverTrash == "off"){
                        messengerReceiver.receiverTrash = "isReceiverOff"
                        messengerReceiver.save(flush: true)
                    //}
                }
            }
        }

        /*def trashMessage = messenger.save(flush: true)
        if(!trashMessage){
            flash.message = "Message not delete in "+action+" box!"
            redirect(action: action)
            return
        }*/
        flash.message = "Message delete form "+action+" box!"
        redirect(action: action)
    }

    def save(){
        if (!request.method == 'POST') {
            flash.message = "Message not send!"
            redirect(action: 'inbox')
            return
        }
        User user = getAuthenticatedUser()
        Registration senderReg = Registration.findByUser(user)

        Messenger messenger
        for (def userId in params.username){
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
        if (!request.method == 'POST') {
            flash.message = "Message Request Type Problem!"
            redirect(action: 'inbox')
            return
        }
        Messenger aMessenger = Messenger.get(params.id as Long)
        List<Messenger> aMessageList = Messenger.findAllByMessenger(aMessenger)
        if (!aMessenger){
            flash.message = "Message invalid!"
            redirect(action: 'inbox')
            return
        }

        if(params.inbox == "inbox"){
            if (aMessageList != null){
                aMessenger.isRead = true
                aMessenger.save(flash:true)
            }
            for (def a in aMessageList){
                a.isRead = true
                a.save(flash:true)
            }
        }
        render(view: 'messageView', model: [messengerList: aMessenger])
    }

    def reply(){
        if (!request.method == 'POST') {
            flash.message = "Message Request Type Problem!"
            redirect(action: 'inbox')
            return
        }

        User user = getAuthenticatedUser()
        Registration senderReg = Registration.findByUser(user)

        Messenger messenger = Messenger.read(params.parentId as Long)
        if(!messenger){
            flash.message = "Message invalid!"
            redirect(action: 'inbox')
            return
        }

        def subject = messenger.subject
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
            flash.message = "Message Not send, try again!!"
            redirect(action: 'inbox')
            return
        }
        Messenger savedMessenger = messenger.save(flush: true)

        if (!savedMessenger){
            flash.message = "Message Not send!"
            render(view: 'messageSend')
            return
        }

        flash.message = "Reply Message send Success!!"
        Messenger messengerList = Messenger.read(params.parentId as Long)
        render(view: 'messageView', model: [messengerList: messengerList])
    }

    def undo(){
        if (!request.method == 'POST') {
            flash.message = "Message Request Type Problem!"
            redirect(action: 'trash')
            return
        }
        Messenger messenger = Messenger.get(params.id as Long)
        if(!messenger){
            flash.message = "Message invalid!"
            redirect(action: action)
            return
        }
        def subject = messenger.subject.trim()
        User user = getAuthenticatedUser()
        Registration registration = Registration.findByUser(user)

        def messengerSenderList = Messenger.findAllBySenderAndSubjectAndSenderTrash(registration,subject,"isSenderOff")
        //println ">>" + messengerSenderList.size()

        if(messengerSenderList != null){
            for (def messengerSender in messengerSenderList){
                messengerSender.senderTrash = "off"
                messengerSender.save(flush: true)
            }
        }
        def messengerReceiverList = Messenger.findAllByReceiverAndSubjectAndReceiverTrash(registration,subject,"isReceiverOff")
        if(messengerReceiverList != null){
            for (def messengerReceiver in messengerReceiverList){
                messengerReceiver.receiverTrash = "off"
                messengerReceiver.save(flush: true)
            }
        }

        flash.message = "Message Undo form Trash box!"
        redirect(action: 'trash')
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

        def results = c.list (max: iDisplayLength, offset:iDisplayStart) {
            and {
                eq('sender',senderReg)
                ne('receiver',senderReg)
                eq('senderTrash',"off")
            }
            if(sSearch){
                or {
                    ilike('subject',sSearch)
                }
            }
            order(sortColumn, sSortDir)
            distinct('subject')
        }

        int totalCount = results.totalCount

        int serial = iDisplayStart;
        if(totalCount>0){
            if(sSortDir.equalsIgnoreCase('desc')){
                serial =(totalCount+1)-iDisplayStart
            }
            results.each {String subject ->
                Messenger messenger = Messenger.findBySubject(subject)
                if(sSortDir.equalsIgnoreCase('asc')){
                    serial++
                }else{
                    serial--
                }
                /*reply*/
                List<Messenger> subjectList = Messenger.findAllBySubject(subject.trim())
                def reply = ''
                if(subjectList.size() > 1){
                    reply = "("+subjectList.size()+")"  //'<span class="green">(Reply)</span>'
                }
                dataReturns.add([DT_RowId:messenger.id,0:serial,1:messenger.receiver.name+" "+reply,2:messenger.subject,3:messenger.dateOfMessage.format('dd/MM/yyyy'),4:''])
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
                eq('receiverTrash',"off")
                ne('sender',receiverReg)
            }
            if(sSearch){
                or {
                    ilike('subject',sSearch)
                }
            }
            order(sortColumn, sSortDir)
            distinct('subject')
        }

        int totalCount = results.totalCount
        int serial = iDisplayStart;
        if(totalCount>0){
            if(sSortDir.equalsIgnoreCase('desc')){
                serial =(totalCount+1)-iDisplayStart
            }
            results.each {String subject ->
                Messenger messenger = Messenger.findBySubject(subject)
                if(sSortDir.equalsIgnoreCase('asc')){
                    serial++
                }else{
                    serial--
                }
                /* Reply */
                List<Messenger> subjectList = Messenger.findAllBySubject(subject.trim())
                def aArrayList = subjectList.isRead
                def isRead = ''
                if( (false in aArrayList) ){
                    isRead = '<span class="blue">(Read)</span>'
                }
                def reply = ''
                if(subjectList.size() > 1){
                    reply = "("+subjectList.size()+")"  //'<span class="green">(Reply)</span>'
                }
                dataReturns.add([DT_RowId:messenger.id,0:serial,1:messenger.sender.name+" "+reply,2:messenger.subject+" "+isRead,3:messenger.dateOfMessage.format('dd/MM/yyyy'),4:''])
            }
        }
        Map gridData =[iTotalRecords:totalCount,iTotalDisplayRecords:totalCount,aaData:dataReturns]
        String result = gridData as JSON
        render result
    }

    def trashList(){
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

        def results = c.list (max: iDisplayLength, offset:iDisplayStart) {
            or{
                and{
                    eq('senderTrash',"isSenderOff")
                    eq('sender',senderReg)
                }
                and{
                    eq('receiverTrash',"isReceiverOff")
                    eq('receiver',senderReg)
                }
            }
            if(sSearch){
                or {
                    ilike('subject',sSearch)
                }
            }
            order(sortColumn, sSortDir)
            distinct('subject')
        }

        int totalCount = results.totalCount
        int serial = iDisplayStart;
        if(totalCount>0){
            if(sSortDir.equalsIgnoreCase('desc')){
                serial =(totalCount+1)-iDisplayStart
            }
            results.each {String subject ->
                Messenger messenger = Messenger.findBySubject(subject)
                if(sSortDir.equalsIgnoreCase('asc')){
                    serial++
                }else{
                    serial--
                }
                /*reply*/
                List<Messenger> subjectList = Messenger.findAllBySubject(subject.trim())
                def reply = ''
                if(subjectList.size() > 1){
                    reply = "("+subjectList.size()+")"  //'<span class="green">(Reply)</span>'
                }
                dataReturns.add([DT_RowId:messenger.id,0:serial,1:messenger.receiver.name+" "+reply,2:messenger.subject,3:messenger.dateOfMessage.format('dd/MM/yyyy'),4:''])
            }
        }
        Map gridData =[iTotalRecords:totalCount,iTotalDisplayRecords:totalCount,aaData:dataReturns]
        String result = gridData as JSON
        render result

    }
}
