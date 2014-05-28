package wboga.core

class Registration {

    String name
    Date dob
    String email
    String username
    String password
    String answer
    boolean status = true

    //static hasMany = [sender: Messenger]
    static belongsTo = [country: Country, hint: Hint, memberType: MemberType]

    static constraints = {
        name blank: false, nullable: false
        dob blank: false, nullable: false
        email blank: false, nullable: false, unique: true
        username blank: false, nullable: false, unique: true
        password blank: false, nullable: false
        answer blank: false, nullable: false
        status blank: true, nullable: true
    }
}
