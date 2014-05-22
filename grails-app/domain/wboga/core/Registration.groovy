package wboga.core

class Registration {

    String name
    Date dob
    String email
    String username
    String password
    String answer
    boolean status = true

    static belongsTo = [country: Country, hint: Hint, memberType: MemberType]

    static constraints = {
        name blank: true, nullable: true
        dob blank: true, nullable: true
        email blank: true, nullable: true
        username blank: true, nullable: true
        password blank: true, nullable: true
        answer blank: true, nullable: true
        status blank: false, nullable: false
    }
}
