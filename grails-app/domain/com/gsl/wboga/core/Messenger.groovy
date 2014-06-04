package com.gsl.wboga.core

class Messenger {

    Date dateOfMessage = new Date()
    String subject
    String messageBody
    Boolean isRead = false
    Registration receiver
    Registration sender
    String receiverTrash = "off"
    String senderTrash = "off"

    static hasMany = [messengers: Messenger]
    static belongsTo = [messenger: Messenger] //receiver: Registration,sender: Registration


    static mapping = {
        messageBody type: 'text'
    }

    static constraints = {
        // myTextField(size:0..65535)
        subject blank:true, nullable:true
        messageBody blank:true, nullable:true
    }
}
