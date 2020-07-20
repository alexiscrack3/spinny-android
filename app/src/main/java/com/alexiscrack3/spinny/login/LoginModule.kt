package com.alexiscrack3.spinny.login

import com.alexiscrack3.spinny.NetworkModule
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loginModule = module {
    factory { NetworkModule.createService(LoginService::class.java) }
    factory { LoginRepository(loginService = get()) }
    viewModel {
        LoginViewModel(
            loginRepository = get(),
            securePreferences = get()
        )
    }
}
