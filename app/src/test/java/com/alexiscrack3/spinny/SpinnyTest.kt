package com.alexiscrack3.spinny

import android.app.Application
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.LayoutRes
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.AutoCloseKoinTest
import org.koin.test.mock.MockProviderRule
import org.mockito.Mockito
import org.robolectric.annotation.Config

@Config(
    application = TestApplication::class,
    manifest = Config.NONE,
    sdk = [Build.VERSION_CODES.P]
)
@RunWith(AndroidJUnit4::class)
abstract class SpinnyTest : AutoCloseKoinTest() {
    protected val context by lazy { ApplicationProvider.getApplicationContext<Application>() }

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        Mockito.mock(clazz.java)
    }

    @Before
    open fun setUp() {
        startKoin { }
        SpinnyModule.init()
    }

    @After
    open fun tearDown() {
        stopKoin()
    }

    fun inflateView(
        @LayoutRes layoutId: Int,
        enclosingLayoutFactory: (Context) -> ViewGroup = { LinearLayout(it) },
        attachToRoot: Boolean = false
    ): View {
        return LayoutInflater.from(context)
            .inflate(layoutId, enclosingLayoutFactory(context), attachToRoot)
    }
}
