package wboga.core

class Hint {

    String question

    static hasMany = [registration: Registration]

    static constraints = {
        question blank: true, nullable: true
    }
}
