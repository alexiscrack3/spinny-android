package com.alexiscrack3.spinny

import com.alexiscrack3.spinny.api.servicesModule
import com.alexiscrack3.spinny.clubs.clubsModule
import com.alexiscrack3.spinny.db.databaseModule
import com.alexiscrack3.spinny.login.loginModule
import com.alexiscrack3.spinny.security.securityModule
import org.koin.core.context.loadKoinModules

object SpinnyModule {

    fun init() {
        val moduleList = listOf(
            databaseModule,
            servicesModule,
            securityModule,
            loginModule,
            clubsModule
        )
        loadKoinModules(moduleList)
    }
}
