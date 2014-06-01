import com.gsl.wboga.uma.security.RequestMap
import com.gsl.wboga.uma.security.Role
import com.gsl.wboga.uma.security.User
import com.gsl.wboga.uma.security.UserRole
import grails.plugin.springsecurity.SpringSecurityUtils
import com.gsl.wboga.core.Country
import com.gsl.wboga.core.Hint
import com.gsl.wboga.core.MemberType
import com.gsl.wboga.core.Registration

class BootStrap {
    def grailsApplication

    Role role
    def init = { servletContext ->
        createUserWithRole()

        // rumee
        createHint()
        createPackage()
        createCountryList()
        createCommonRole()
        createCommonReqMap()
        createAccessReqMap()
        createRegistration() // default User add
    }

    def createRegistration(){

        def superAdmin = Role.findByAuthority('ROLE_MEMBER') ?: new Role(authority: 'ROLE_MEMBER').save(failOnError: true)
        // Rumee User
        User userOne = User.findByUsername('rumee')
        if (!userOne) {
            userOne = new User(username: 'rumee', email: 'rumee@mail.com', password: 'rumeegsl', enabled: true, accountExpired: false, accountLocked: false, passwordExpired: false)
            def userOneSaved = userOne.save(flush: true)
            new UserRole(user: userOneSaved, role: superAdmin).save(flush: true)

            new Registration(
                    name: "Rumee",
                    dob: new Date("13/05/1970"),
                    answer: "abc",
                    country: 1,
                    hint: 2,
                    memberType: 1,
                    user: userOneSaved
            ).save(failOnError: true)
        }

        // Imran User
        User userTwo = User.findByUsername('imran')
        if (!userTwo) {
            userTwo = new User(username: 'imran', email: 'imran@mail.com', password: 'imrangsl', enabled: true, accountExpired: false, accountLocked: false, passwordExpired: false)
            def userTwoSaved = userTwo.save(flush: true)
            new UserRole(user: userTwoSaved, role: superAdmin).save(flush: true)

            new Registration(
                    name: "Imran",
                    dob: new Date("13/05/1970"),
                    answer: "abc",
                    country: 1,
                    hint: 2,
                    memberType: 1,
                    user: userTwoSaved
            ).save(failOnError: true)
        }

        // Imran User
        User userThree = User.findByUsername('jalal')
        if (!userThree) {
            userThree = new User(username: 'jalal', email: 'jalal@mail.com', password: 'jalalgsl', enabled: true, accountExpired: false, accountLocked: false, passwordExpired: false)
            def userThreeSaved = userThree.save(flush: true)
            new UserRole(user: userThreeSaved, role: superAdmin).save(flush: true)

            new Registration(
                    name: "Jalal",
                    dob: new Date("13/05/1970"),
                    answer: "abc",
                    country: 1,
                    hint: 2,
                    memberType: 1,
                    user: userThreeSaved
            ).save(failOnError: true)
        }

    }

    def createHint(){
        new Hint(question: "What is your feb color ?").save(failOnError: true)
        new Hint(question: "What is the name of your best friend ?").save(failOnError: true)
        new Hint(question: "What was the name of your third grade teacher?").save(failOnError: true)
        new Hint(question: "What is the name of your favorite childhood friend?").save(failOnError: true)
        new Hint(question: "Where were you New Year's 2000?").save(failOnError: true)
        new Hint(question: "What is the name of your grandmother's dog?").save(failOnError: true)
        new Hint(question: "What is your youngest sister's birthday?").save(failOnError: true)
        new Hint(question: "What is your youngest brother's name?").save(failOnError: true)
        new Hint(question: "What was your childhood nickname?").save(failOnError: true)
        new Hint(question: "Who was your childhood hero?").save(failOnError: true)
        new Hint(question: "What school did you attend for sixth grade?").save(failOnError: true)
        new Hint(question: "In what city or town was your first job?").save(failOnError: true)
        new Hint(question: "What is the name of the company of your first job?").save(failOnError: true)
        new Hint(question: "What was your favorite place to visit as a child?").save(failOnError: true)
        new Hint(question: "What was your dream job as a child? ").save(failOnError: true)
        new Hint(question: "What are the last 5 of your Social Security number?").save(failOnError: true)
        new Hint(question: "What are the last 5 digits of your credit card?").save(failOnError: true)
        new Hint(question: "What is your mother's middle name? ").save(failOnError: true)
        new Hint(question: "What is your grandmother's first name?").save(failOnError: true)
        new Hint(question: "Where did you vacation last year?").save(failOnError: true)
    }

    def createPackage(){
        new MemberType(name: "Free", amount:0).save(failOnError: true)
        new MemberType(name: "Premium", amount:100).save(failOnError: true)
        new MemberType(name: "Silver", amount:200).save(failOnError: true)
        new MemberType(name: "Gold", amount:300).save(failOnError: true)
    }

    def createCountryList() {
        def bangladesh = Country.findByName("BANGLADESH")
        if (!bangladesh) {
            bangladesh = new Country(name: "BANGLADESH").save(failOnError: true)
        }

        def afghanistan = Country.findByName("AFGHANISTAN")
        if (!afghanistan) {
            afghanistan = new Country(name: "AFGHANISTAN").save(failOnError: true)
        }

        def nigeria = Country.findByName("NIGERIA")
        if (!nigeria) {
            nigeria = new Country(name: "NIGERIA").save(failOnError: true)
        }
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

    def createCommonRole(){
        role = new Role(id: 3, authority: 'ROLE_MEMBER')
        role.save(flush: true)
    }

    def destroy = {
    }

    def createCommonReqMap(){
        new RequestMap(url: '/login/**', configAttribute: 'IS_AUTHENTICATED_ANONYMOUSLY').save(flush: true)
        new RequestMap(url: '/login', configAttribute: 'IS_AUTHENTICATED_ANONYMOUSLY').save(flush: true)
        new RequestMap(url: '/logout/**', configAttribute: 'IS_AUTHENTICATED_ANONYMOUSLY').save(flush: true)
        new RequestMap(url: '/**/js/**', configAttribute: 'IS_AUTHENTICATED_ANONYMOUSLY').save(flush: true)
        new RequestMap(url: '/**/css/**', configAttribute: 'IS_AUTHENTICATED_ANONYMOUSLY').save(flush: true)
        new RequestMap(url: '/**/images/**', configAttribute: 'IS_AUTHENTICATED_ANONYMOUSLY').save(flush: true)
        new RequestMap(url: '/dashboard/index', configAttribute: 'ROLE_ADMIN,ROLE_MEMBER').save(flush: true)
        new RequestMap(url: '/simpleCaptcha/captcha', configAttribute: 'IS_AUTHENTICATED_ANONYMOUSLY').save(flush: true)
        new RequestMap(url: '/registration/create', configAttribute: 'IS_AUTHENTICATED_ANONYMOUSLY').save(flush: true)
        new RequestMap(url: '/registration/save', configAttribute: 'IS_AUTHENTICATED_ANONYMOUSLY').save(flush: true)
        new RequestMap(url: '/', configAttribute: 'ROLE_SUPER_ADMIN,ROLE_ADMIN,ROLE_MEMBER').save(flush: true)
    }

    def createAccessReqMap(){
        // Messenger Controller
        new RequestMap(url: '/messenger/inbox',     configAttribute: 'ROLE_MEMBER').save(flush: true)
        new RequestMap(url: '/messenger/send',      configAttribute: 'ROLE_MEMBER').save(flush: true)
        new RequestMap(url: '/messenger/compose',   configAttribute: 'ROLE_MEMBER').save(flush: true)
        new RequestMap(url: '/messenger/trash',     configAttribute: 'ROLE_MEMBER').save(flush: true)
        new RequestMap(url: '/messenger/chat',      configAttribute: 'ROLE_MEMBER').save(flush: true)
        new RequestMap(url: '/messenger/save',      configAttribute: 'ROLE_MEMBER').save(flush: true)
        new RequestMap(url: '/messenger/sendList',  configAttribute: 'ROLE_MEMBER').save(flush: true)
        new RequestMap(url: '/messenger/inboxList', configAttribute: 'ROLE_MEMBER').save(flush: true)
        new RequestMap(url: '/messenger/view',      configAttribute: 'ROLE_MEMBER').save(flush: true)
        new RequestMap(url: '/messenger/reply',      configAttribute: 'ROLE_MEMBER').save(flush: true)
    }
}
