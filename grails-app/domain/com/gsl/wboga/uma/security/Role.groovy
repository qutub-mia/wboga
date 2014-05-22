package com.gsl.wboga.uma.security

class Role {

	String authority
    boolean status = true

	static mapping = {
		cache true
	}

	static constraints = {
		authority blank: false, unique: true
	}
}
