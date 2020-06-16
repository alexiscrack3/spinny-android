package com.alexiscrack3.spinny

import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.runner.RunWith
import org.koin.core.context.startKoin
import org.koin.test.AutoCloseKoinTest
import org.robolectric.annotation.Config

@Config(
    application = TestApplication::class,
    manifest = Config.NONE,
    sdk = [Build.VERSION_CODES.P]
)
@RunWith(AndroidJUnit4::class)
abstract class SpinnyTest : AutoCloseKoinTest() {

    @Before
    open fun setUp() {
        startKoin { }
        SpinnyModule.init()
    }
}
