package com.alexiscrack3.spinny

import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.alexiscrack3.spinny.api.servicesModule
import com.alexiscrack3.spinny.clubs.clubsModule
import com.alexiscrack3.spinny.login.loginModule
import com.alexiscrack3.spinny.security.securityModule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.AutoCloseKoinTest
import org.koin.test.check.checkModules
import org.robolectric.annotation.Config
import timber.log.Timber

@Config(
    manifest = Config.NONE,
    sdk = [Build.VERSION_CODES.P]
)
@RunWith(AndroidJUnit4::class)
class SpinnyApplicationTest : AutoCloseKoinTest() {

    @Test
    fun `check all modules`() {
        checkModules {
            val moduleList = listOf(
                storageModule,
                servicesModule,
                securityModule,
                loginModule,
                clubsModule
            )
            modules(moduleList)
        }
    }
}
