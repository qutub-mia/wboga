package wboga.core

class MemberType {

    String name
    String amount

    static hasMany = [registration: Registration]

    static constraints = {
        name blank: true, nullable: true
        amount blank: true, nullable: true
    }
}
