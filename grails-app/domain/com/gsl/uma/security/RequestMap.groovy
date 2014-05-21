package com.gsl.uma.security

import org.springframework.http.HttpMethod

class RequestMap {

	String url
	String configAttribute
	HttpMethod httpMethod
//    Long moduleId = 0L        //ModuleId, FeatureId, FeventsId
//    Long featureId = 0L        //ModuleId, FeatureId, FeventsId
//    Long eventId
//    Long childOf
//    boolean autoPermit = false
//    String menuText
//    String description

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
