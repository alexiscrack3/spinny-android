package com.alexiscrack3.spinny.login

import android.widget.Button
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.alexiscrack3.spinny.R
import com.alexiscrack3.spinny.launchFragmentInHiltContainer
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import com.google.common.truth.Truth.assertThat

//@Config(manifest = Config.NONE)
//@Config(manifest = "src/debug/AndroidManifest.xml")
@RunWith(AndroidJUnit4::class)
class LoginFragmentTest {

    @Test
    fun testEventFragment() {
        launchFragmentInHiltContainer<LoginFragment> {
            val button = this.view?.findViewById<Button>(R.id.login_button)
            assertThat(button?.text.toString()).isEqualTo("Login")
        }
    }
}