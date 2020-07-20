package com.alexiscrack3.spinny

import com.alexiscrack3.spinny.clubs.clubsModule
import com.alexiscrack3.spinny.login.loginModule
import com.alexiscrack3.spinny.security.securityModule
import org.koin.core.context.loadKoinModules

object SpinnyModule {

    fun init() {
        NetworkModule.init()

        val moduleList = listOf(
            securityModule,
            loginModule,
            clubsModule
        )
        loadKoinModules(moduleList)
    }
}
