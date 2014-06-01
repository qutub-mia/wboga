package com.gsl.wboga.core

import com.gsl.wboga.uma.security.User

class Registration {

    String name
    Date dob
    String answer
    boolean status = true

    static belongsTo = [country: Country, hint: Hint, memberType: MemberType, user: User]

    static constraints = {
        name blank: false, nullable: false
        dob blank: false, nullable: false
        answer blank: false, nullable: false
        status blank: true, nullable: true
    }
}
