package com.gsl.wboga.core

class Country {
    String name

    static hasMany = [registration: Registration]
    static constraints = {

    }
}
