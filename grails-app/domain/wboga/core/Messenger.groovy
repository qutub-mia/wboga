package wboga.core

class Messenger {

    Date dateOfMessage
    String subject
    String messageBody
    boolean showOn = false
    String messagerFile


    static hasMany = [messengers: Messenger]
    static belongsTo = [sender: Registration, receiver: Registration, messenger: Messenger]

    static constraints = {
        dateOfMessage blank:true, nullable:true
        subject blank:true, nullable:true
        messageBody blank:true, nullable:true
        showOn blank:true, nullable:true
        messagerFile blank: true, nullable: true
        messenger nullable: true, blank: true
    }
}
