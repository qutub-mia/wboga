package com.gsl.wboga.core

class MemberType {

    String name
    Double amount

    static hasMany = [registration: Registration]

    static constraints = {
        name blank: true, nullable: true
        amount blank: true, nullable: true
    }
}
