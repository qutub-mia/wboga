import com.gsl.wboga.uma.security.RequestMap
import com.gsl.wboga.uma.security.Role
import com.gsl.wboga.uma.security.User
import com.gsl.wboga.uma.security.UserRole
import grails.plugin.springsecurity.SpringSecurityUtils
import wboga.core.Country
import wboga.core.Hint
import wboga.core.MemberType
import wboga.core.Registration

class BootStrap {
    def grailsApplication
    def init = { servletContext ->
        createUserWithRole()

        // rumee
        createHint()
        createPackage()
        createCountryList()
        createCommonReqMap()
        createRegistration()
    }

    def createRegistration(){
        new Registration(
                name: "Nusrat",
                dob: new Date("13/05/1970"),
                email:"n@gmail.com",
                username: "nusrat",
                password: "nusrat",
                answer: "abc",
                country: 1,
                hint: 2,
                memberType: 1
        ).save(failOnError: true)

        new Registration(
                name: "Rumee",
                dob: new Date("13/05/1970"),
                email:"r@gmail.com",
                username: "rumee",
                password: "rumee",
                answer: "abc",
                country: 1,
                hint: 2,
                memberType: 1
        ).save(failOnError: true)


        new Registration(
                name: "Imram",
                dob: new Date("13/05/1970"),
                email:"i@gmail.com",
                username: "imran",
                password: "imran",
                answer: "abc",
                country: 1,
                hint: 2,
                memberType: 1
        ).save(failOnError: true)


        new Registration(
                name: "Murshida",
                dob: new Date("13/05/1970"),
                email:"m@gmail.com",
                username: "murshida",
                password: "murshida",
                answer: "abc",
                country: 1,
                hint: 2,
                memberType: 1
        ).save(failOnError: true)
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
        new MemberType(name: "Free", amount:"0").save(failOnError: true)
        new MemberType(name: "Premium", amount:"100").save(failOnError: true)
        new MemberType(name: "Silver", amount:"200").save(failOnError: true)
        new MemberType(name: "Gold", amount:"300").save(failOnError: true)
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

    def createCommonReqMap(){
        new RequestMap(url: '/registration/create', configAttribute: 'IS_AUTHENTICATED_ANONYMOUSLY').save(flush: true)
    }

    def destroy = {
    }
}
