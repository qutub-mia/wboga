package com.gsl.wboga.uma.security

import org.springframework.http.HttpMethod

class RequestMap {

	String url
	String configAttribute
	HttpMethod httpMethod
	static mapping = {
		cache true
	}

	static constraints = {
		url blank: false, unique: true
		configAttribute blank: false
		httpMethod nullable: true
//        eventId nullable: true
//        childOf nullable: true
//        description nullable: true
	}
}
