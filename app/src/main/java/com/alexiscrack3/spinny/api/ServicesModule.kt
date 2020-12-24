package com.alexiscrack3.spinny.api

import org.koin.dsl.module

var servicesModule = module {
    factory {
        AuthTokenInterceptor(
            securePreferences = get()
        )
    }
    single {
        ServicesFactory(
            authTokenInterceptor = get<AuthTokenInterceptor>()
        )
    }
}
