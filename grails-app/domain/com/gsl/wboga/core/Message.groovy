package com.gsl.wboga.core

import com.gsl.wboga.uma.security.User

class Message {

    Date createdBy = new Date()
    String subject
    String body
    Boolean isRead = false
    User receiver
    User sender
    Integer isReceiverTrash = 0
    Integer isSenderTrash = 0
    Integer parentId

    //static hasMany = [messengers: Message]
    //static belongsTo = [messenger: Message] //receiver: Registration,sender: Registration

    static mapping = {
        body type: 'text'
    }

    static constraints = {
        subject blank: true, nullable: true
        body blank: true, nullable: true
        isReceiverTrash blank: true, nullable: true
        isSenderTrash blank: true, nullable: true
        parentId blank:true, nullable: true
    }
}
