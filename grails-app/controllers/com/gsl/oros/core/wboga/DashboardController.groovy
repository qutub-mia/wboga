package com.gsl.oros.core.wboga

import grails.plugin.springsecurity.annotation.Secured


class DashboardController {
    @Secured(['ROLE_SUPER_ADMIN'])
    def index() {
        render(view: 'index')
    }
}
