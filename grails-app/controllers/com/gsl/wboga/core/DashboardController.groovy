package com.gsl.wboga.core

import grails.plugin.springsecurity.annotation.Secured

class DashboardController {
    def index() {
        render(view: '/dashboard/index')
    }
}
