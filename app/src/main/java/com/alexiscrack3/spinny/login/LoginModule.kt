package com.alexiscrack3.spinny.login

import com.alexiscrack3.spinny.api.ServicesFactory
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loginModule = module {
    factory {
        val serviceFactory = get<ServicesFactory>()
        serviceFactory.createService(LoginService::class.java)
    }
    factory { LoginRepository(loginService = get()) }
    viewModel {
        LoginViewModel(
            loginRepository = get(),
            securePreferences = get()
        )
    }
    viewModel {
        EnrollmentViewModel(
            loginRepository = get()
        )
    }
}
