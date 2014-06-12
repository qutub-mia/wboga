package com.gsl.wboga.core

import com.gsl.wboga.uma.security.User
import grails.converters.JSON


class MessengerController {

    def inbox() {
        User user = getAuthenticatedUser()
        render(view: 'messageInbox', model: [userId: user.id])
    }

    def send() {
        User user = getAuthenticatedUser()
        render(view: 'messageSend', model: [userId: user.id])
    }

    def compose() {
        render(view: '_compose')
    }

    def trash() {
        User user = getAuthenticatedUser()
        render(view: 'messageTrash', model: [userId: user.id])
    }

    def trashDelete() {
        if (!request.method == 'POST') {
            flash.message = "Message not Delete!"
            redirect(action: 'inbox')
            return
        }

        Message messenger = Message.get(params.id as Long)
        if (!messenger) {
            flash.message = "Message invalid!"
            redirect(action: 'trash')
            return
        }

        Integer parentId = messenger.parentId
        User user = getAuthenticatedUser()

        def messengerSenderList = Message.findAllBySenderAndParentIdAndIsSenderTrash(user, parentId, 1)
        if (messengerSenderList) {
            for (def messengerSender in messengerSenderList) {
                messengerSender.isSenderTrash = 2
                messengerSender.save(flush: true)
            }
        }

        def messengerReceiverList = Message.findAllByReceiverAndParentIdAndIsReceiverTrash(user, parentId, 1)
        if (messengerReceiverList) {
            for (def messengerReceiver in messengerReceiverList) {
                messengerReceiver.isReceiverTrash = 2
                messengerReceiver.save(flush: true)
            }
        }

        // Delete message, when sender and receiver all delete message from trash !
        if ((messenger.isSenderTrash == 2) && (messenger.isReceiverTrash == 2)) {
            def count = 0
            def countList = Message.findAllByParentId(params.id as Integer)
            for (def i=0; i<countList.size(); i++){
                if(countList.isReceiverTrash[i] && countList.isSenderTrash[i]){
                    count++
                }
            }
            if( count == countList.size() ){
                def pId = params.id as Integer
                def successDelete = Message.where {parentId == pId}.deleteAll()
                if(!successDelete){
                    flash.message = "Message delete not success form trash box!"
                    redirect(action: 'trash')
                    return
                }
                flash.message = "Message delete form trash box!"
                redirect(action: 'trash')
                return
            }
        }

        flash.message = "Message delete form trash box!"
        redirect(action: 'trash')
    }

    def delete() {
        if (!request.method == 'POST') {
            flash.message = "Message not Delete!"
            redirect(action: 'inbox')
            return
        }
        String action = (params.view as String).trim()
        Message messenger = Message.get(params.id as Long)
        if (!messenger) {
            flash.message = "Message invalid!"
            redirect(action: action)
            return
        }

        User user = getAuthenticatedUser()
        Integer parentId  = messenger.parentId

        if (action == "send") {
            def messengerSenderList = Message.findAllBySenderAndParentIdAndIsSenderTrash(user, parentId, 0)
            if (messengerSenderList != null) {
                for (def messengerSender in messengerSenderList) {
                    messengerSender.isSenderTrash = 1
                    messengerSender.save(flush: true)
                }
            }
        }

        if (action == "inbox") {
            def messengerReceiverList = Message.findAllByReceiverAndParentIdAndIsReceiverTrash(user, parentId, 0)
            if (messengerReceiverList != null) {
                for (def messengerReceiver in messengerReceiverList) {
                    messengerReceiver.isReceiverTrash = 1
                    messengerReceiver.save(flush: true)
                }
            }
        }

        flash.message = "Message delete form " + action + " box!"
        redirect(action: action)
    }

    def save() {
        if (!request.method == 'POST') {
            flash.message = "Message not send!"
            redirect(action: 'inbox')
            return
        }
        User user = getAuthenticatedUser()

        Message messenger

        for (def userId in params.user) {
            String subject = params.subject
            String body = params.body
            def receiver = userId

            messenger = new Message(
                    subject: subject,
                    body: body,
                    sender: user,
                    receiver: receiver,
            )

            if (!messenger.validate()) {
                flash.message = "Not added validated!"
                redirect(action: 'inbox')
                return
            }

            Message savedMessenger = messenger.save(flush: true)
            if (!savedMessenger) {
                flash.message = "Not Added!"
                render(view: 'messageSend')
                return
            }
            savedMessenger.parentId = savedMessenger.id
        }

        flash.message = "Send Message Successfully!!"
        render(view: 'messageSend', model: [userId: user.id])
    }

    def view() {
        if (!request.method == 'POST') {
            flash.message = "Message Request Type Problem!"
            redirect(action: 'inbox')
            return
        }
        Message aMessenger = Message.get(params.id as Long)
        List<Message> aMessageList = Message.findAllByParentId(aMessenger.parentId as Integer)

        if (!aMessenger) {
            flash.message = "Message invalid!"
            redirect(action: 'inbox')
            return
        }

        if (params.inbox == "inbox") {
            if (aMessageList != null) {
                aMessenger.isRead = true
                aMessenger.save(flash: true)
            }
            for (def a in aMessageList) {
                a.isRead = true
                a.save(flash: true)
            }
        }
        render(view: 'messageView', model: [messengerList: aMessageList])
    }

    def reply() {
        if (!request.method == 'POST') {
            flash.message = "Message Request Type Problem!"
            redirect(action: 'inbox')
            return
        }

        User user = getAuthenticatedUser()
        Message messenger = Message.findByParentId(params.parentId as Integer)
        if (!messenger) {
            flash.message = "Message invalid!"
            redirect(action: 'inbox')
            return
        }
        String subject = messenger.subject
        String body = params.body
        User receiver = messenger.sender
        Integer parentId = messenger.parentId

        messenger = new Message(
                subject: subject,
                body: body,
                sender: user,
                receiver: receiver,
                parentId: parentId
        )

        if (!messenger.validate()) {
            flash.message = "Message Not send, try again!!"
            redirect(action: 'inbox')
            return
        }
        Message savedMessenger = messenger.save(flush: true)

        if (!savedMessenger) {
            flash.message = "Message Not send!"
            render(view: 'messageSend')
            return
        }

        if (savedMessenger.sender.id == savedMessenger.receiver.id){
            Message message = Message.findById(savedMessenger.parentId as Long)
            message.isReceiverTrash = 0
            message.save(flush: true)
        }

        flash.message = "Reply Message send Success!!"
        def messengerList = Message.findAllByParentId(params.parentId as Integer)
        render(view: 'messageView', model: [messengerList: messengerList])
    }

    def undo() {
        if (!request.method == 'POST') {
            flash.message = "Message Request Type Problem!"
            redirect(action: 'trash')
            return
        }
        Message messenger = Message.get(params.id as Long)
        if (!messenger) {
            flash.message = "Message invalid!"
            redirect(action: 'trash')
            return
        }
        User user = getAuthenticatedUser()
        Integer parentId  = messenger.parentId

        List<Message> messengerSenderList = Message.findAllBySenderAndParentIdAndIsSenderTrash(user, parentId, 1)

        if (messengerSenderList != null) {
            for (def messengerSender in messengerSenderList) {
                messengerSender.isSenderTrash = 0
                messengerSender.save(flush: true)
            }
        }
        List<Message> messengerReceiverList = Message.findAllByReceiverAndParentIdAndIsReceiverTrash(user, parentId, 1)
        if (messengerReceiverList != null) {
            for (def messengerReceiver in messengerReceiverList) {
                messengerReceiver.isReceiverTrash = 0
                messengerReceiver.save(flush: true)
            }
        }

        flash.message = "Message Undo form Trash box!"
        redirect(action: 'trash')
    }

    def sendList() {
        User user = getAuthenticatedUser()
        //User senderReg = User.findById(user)

        int sEcho = params.sEcho ? params.getInt('sEcho') : 1
        int iDisplayStart = params.iDisplayStart ? params.getInt('iDisplayStart') : 0
        int iDisplayLength = params.iDisplayLength ? params.getInt('iDisplayLength') : 10
        String sSortDir = params.sSortDir_0 ? params.sSortDir_0 : 'asc'
        int iSortingCol = params.iSortingCols ? params.getInt('iSortingCols') : 1
        //Search string, use or logic to all fields that required to include
        String sSearch = params.sSearch ? params.sSearch : null

        if (sSearch) {
            sSearch = "%" + sSearch + "%"
        }

        String sortColumn = MessengerUtility.getSortColumn(iSortingCol)
        List dataReturns = new ArrayList()
        def c = Message.createCriteria()

        def results = c.list(max: iDisplayLength, offset: iDisplayStart) {
            and {
                eq('sender', user)
                eq('isSenderTrash', 0)
                ne('receiver', user)  // when not use distinct
            }
            if (sSearch) {
                or {
                    ilike('subject', sSearch)
                }
            }
            order(sortColumn, sSortDir)
            distinct('parentId')
        }


        int totalCount = results.totalCount

        int serial = iDisplayStart;
        if (totalCount > 0) {
            if (sSortDir.equalsIgnoreCase('desc')) {
                serial = (totalCount + 1) - iDisplayStart
            }
            results.each { Integer parentId ->
                if (sSortDir.equalsIgnoreCase('asc')) {
                    serial++
                } else {
                    serial--
                }

                /*reply*/
                Message messenger = Message.findByParentId(parentId)
                List<Message> subjectList = Message.findAllByParentId(parentId)
                def reply = ''
                if (subjectList.size() > 1) {
                    reply = "(" + subjectList.size() + ")"  //'<span class="green">(Reply)</span>'
                }
                dataReturns.add([DT_RowId: messenger.id, 0: serial, 1: messenger.receiver.username + " " + reply, 2: messenger.subject, 3: messenger.createdBy.format('dd/MM/yyyy'), 4: ''])
            }
        }
        Map gridData = [iTotalRecords: totalCount, iTotalDisplayRecords: totalCount, aaData: dataReturns]
        String result = gridData as JSON
        render result
    }

    def inboxList() {
        User user = getAuthenticatedUser()

        int sEcho = params.sEcho ? params.getInt('sEcho') : 1
        int iDisplayStart = params.iDisplayStart ? params.getInt('iDisplayStart') : 0
        int iDisplayLength = params.iDisplayLength ? params.getInt('iDisplayLength') : 10
        String sSortDir = params.sSortDir_0 ? params.sSortDir_0 : 'asc'
        int iSortingCol = params.iSortingCols ? params.getInt('iSortingCols') : 1
        //Search string, use or logic to all fields that required to include
        String sSearch = params.sSearch ? params.sSearch : null
        if (sSearch) {
            sSearch = "%" + sSearch + "%"
        }

        String sortColumn = MessengerUtility.getSortColumn(iSortingCol)
        List dataReturns = new ArrayList()
        def c = Message.createCriteria()
        def results = c.list(max: iDisplayLength, offset: iDisplayStart) {
            and {
                eq('receiver', user)
                eq('isReceiverTrash', 0)
            }
            if (sSearch) {
                or {
                    ilike('subject', sSearch)
                }
            }
            order(sortColumn, sSortDir)
            distinct('parentId')
        }

        int totalCount = results.totalCount
        int serial = iDisplayStart;
        if (totalCount > 0) {
            if (sSortDir.equalsIgnoreCase('desc')) {
                serial = (totalCount + 1) - iDisplayStart
            }
            results.each { Integer parentId ->
                if (sSortDir.equalsIgnoreCase('asc')) {
                    serial++
                } else {
                    serial--
                }
                Message messenger = Message.findByParentId(parentId)
                List<Message> messageList = Message.findAllByParentId(parentId)
                def aArrayList = messenger.isRead
                def isRead = ''
                if ((false in aArrayList)) {
                    isRead = '<span class="blue">(Read)</span>'
                }
                def reply = ''
                if (messageList.size() > 1) {
                    reply = "(" + messageList.size() + ")"
                }
                dataReturns.add([DT_RowId: messenger.id, 0: serial, 1: messenger.sender.username + " " + reply, 2: messenger.subject + " " + isRead, 3: messenger.createdBy.format('dd/MM/yyyy'), 4: ''])
            }
        }
        Map gridData = [iTotalRecords: totalCount, iTotalDisplayRecords: totalCount, aaData: dataReturns]
        String result = gridData as JSON
        render result
    }

    def trashList() {
        User user = getAuthenticatedUser()

        int sEcho = params.sEcho ? params.getInt('sEcho') : 1
        int iDisplayStart = params.iDisplayStart ? params.getInt('iDisplayStart') : 0
        int iDisplayLength = params.iDisplayLength ? params.getInt('iDisplayLength') : 10
        String sSortDir = params.sSortDir_0 ? params.sSortDir_0 : 'asc'
        int iSortingCol = params.iSortingCols ? params.getInt('iSortingCols') : 1
        //Search string, use or logic to all fields that required to include
        String sSearch = params.sSearch ? params.sSearch : null

        if (sSearch) {
            sSearch = "%" + sSearch + "%"
        }

        String sortColumn = MessengerUtility.getSortColumn(iSortingCol)
        List dataReturns = new ArrayList()
        def c = Message.createCriteria()

        def results = c.list(max: iDisplayLength, offset: iDisplayStart) {
            or {
                and {
                    eq('isSenderTrash', 1)
                    eq('sender', user)
                }
                and {
                    eq('isReceiverTrash', 1)
                    eq('receiver', user)
                }
            }
            if (sSearch) {
                or {
                    ilike('subject', sSearch)
                }
            }
            order(sortColumn, sSortDir)
            distinct('parentId')
        }

        int totalCount = results.totalCount
        int serial = iDisplayStart;
        if (totalCount > 0) {
            if (sSortDir.equalsIgnoreCase('desc')) {
                serial = (totalCount + 1) - iDisplayStart
            }
            results.each { Integer parentId ->
                if (sSortDir.equalsIgnoreCase('asc')) {
                    serial++
                } else {
                    serial--
                }
                Message messenger = Message.findByParentId(parentId)
                List<Message> messageListList = Message.findAllByParentId(parentId)
                def reply = ''
                if (messageListList.size() > 1) {
                    reply = "(" + messageListList.size() + ")"
                }
                dataReturns.add([DT_RowId: messenger.id, 0: serial, 1: messenger.receiver.username + " " + reply, 2: messenger.subject, 3: messenger.createdBy.format('dd/MM/yyyy'), 4: ''])
            }
        }
        Map gridData = [iTotalRecords: totalCount, iTotalDisplayRecords: totalCount, aaData: dataReturns]
        String result = gridData as JSON
        render result

    }
}
