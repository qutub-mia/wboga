package com.gsl.wboga.core

class Messenger {

    Date dateOfMessage = new Date()
    String subject
    String messageBody
    Boolean isRead = false
    Registration receiver
    Registration sender
    String trash = "off"

    static hasMany = [messengers: Messenger]
    static belongsTo = [messenger: Messenger] //receiver: Registration,sender: Registration

    static constraints = {
        subject blank:true, nullable:true
        messageBody blank:true, nullable:true
    }
}
