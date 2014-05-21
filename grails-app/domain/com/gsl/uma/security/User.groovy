package com.gsl.uma.security

class User {

	transient springSecurityService

	String username
    String email
	String password
	boolean enabled = false
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired
    boolean status = true

	static transients = ['springSecurityService']

	static constraints = {
		username blank: false, unique: true
		password blank: false
        email nullable: false, unique: true, email: true
	}

	static mapping = {
		password column: '`password`'
	}

	Set<Role> getAuthorities() {
		UserRole.findAllByUser(this).collect { it.role } as Set
	}

	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
	}

	protected void encodePassword() {
		password = springSecurityService.encodePassword(password)
	}
}
