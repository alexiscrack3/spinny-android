package com.alexiscrack3.spinny.login

import com.alexiscrack3.spinny.api.SignInRequest
import com.alexiscrack3.spinny.api.SignUpRequest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test

class LoginRepositoryTest {

    @Test
    fun `signIn invokes signIn on the login service`() {
        val email = ""
        val password = ""
        val loginService = mock<LoginService>()
        val testObject = LoginRepository(loginService)

        testObject.signIn(email, password)

        verify(loginService).signIn(SignInRequest(email, password))
    }

    @Test
    fun `signUp invokes signUp on the login service`() {
        val email = ""
        val password = ""
        val loginService = mock<LoginService>()
        val testObject = LoginRepository(loginService)

        testObject.signUp(email, password)

        verify(loginService).signUp(SignUpRequest(email, password))
    }
}
