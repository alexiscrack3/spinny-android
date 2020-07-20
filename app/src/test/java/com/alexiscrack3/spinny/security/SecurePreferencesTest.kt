package com.alexiscrack3.spinny.security

import android.content.SharedPreferences
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class SecurePreferencesTest {

    @Test
    fun `setAccessToken should store access token in encrypted shared preference`() {
        val accessToken = "accessToken"
        val editor = mock<SharedPreferences.Editor>()
        whenever(editor.putString("ACCESS_TOKEN", accessToken)).thenReturn(editor)
        val sharedPreferences = mock<SharedPreferences> {
            on { this.edit() } doReturn editor
        }
        val testObject = SecurePreferences(sharedPreferences)

        testObject.setAccessToken(accessToken)

        verify(editor).apply()
    }

    @Test
    fun `getAccessToken should get  acess token in encrypted shared preference`() {
        val accessToken = "accessToken"
        val sharedPreferences = mock<SharedPreferences> {
            on { this.getString("ACCESS_TOKEN", null) } doReturn accessToken
        }
        val testObject = SecurePreferences(sharedPreferences)

        val actual = testObject.getAccessToken()

        assertThat(actual, equalTo(accessToken))
    }
}
