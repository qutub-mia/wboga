import com.gsl.uma.security.Role
import com.gsl.uma.security.User
import com.gsl.uma.security.UserRole
import grails.plugin.springsecurity.SpringSecurityUtils

class BootStrap {

    def init = { servletContext ->
        createUserWithRole()
    }


    void createUserWithRole() {
        def superAdmin = Role.findByAuthority('ROLE_SUPER_ADMIN') ?: new Role(authority: 'ROLE_SUPER_ADMIN').save(failOnError: true)
        User supAdminUser = User.findByUsername('admin')
        if (!supAdminUser) {
            supAdminUser = new User(username: 'admin', email: 'testuser@mail.com', password: 'password', enabled: true, accountExpired: false, accountLocked: false, passwordExpired: false)
            supAdminUser.save(flush: true)
            new UserRole(user: supAdminUser, role: superAdmin).save(flush: true)
        }

        //Role admin
        def roleAdmin = Role.findByAuthority('ROLE_ADMIN') ?: new Role(authority: 'ROLE_ADMIN').save(failOnError: true)
        User adminUser = User.findByUsername('admin2')
        if (!adminUser) {
            adminUser = new User(username: 'admin2', email: 'testuser2@mail.com', password: 'password', enabled: true, accountExpired: false, accountLocked: false, passwordExpired: false)
            adminUser.save(flush: true)
            new UserRole(user: adminUser, role: roleAdmin).save(flush: true)
        }

    }


    def createRequestMap() {
        if (SpringSecurityUtils.securityConfigType != 'Requestmap') {
            return
        }
        // set the below variable to true if requestMap updated
        // and also delete/truncate your database requestmap table to update take effect
        boolean isRequestMapUpdated = true;
        if (!isRequestMapUpdated) {
            return
        }
        String requestMapClassName = SpringSecurityUtils.securityConfig.requestMap.className

        //default configuration entry required for static resources
//        '/', '/index', '/index.gsp',
        def Requestmap = grailsApplication.getClassForName(requestMapClassName)
        def reqInstance
        for (url in ['/**/js/**', '/**/css/**', '/**/images/**', '/**/imageIndirect/index/**', '/**/favicon.ico',
                '/login', '/login/**', '/logout', '/logout/**']) {
            reqInstance = Requestmap.findByUrl(url)
            if (!reqInstance) {
                Requestmap.newInstance(url: url, configAttribute: 'permitAll').save(flush: true, failOnError: true)
            }

        }
        println "Request Map code complete"
    }

    def destroy = {
    }
}
