package com.alexiscrack3.spinny.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.alexiscrack3.spinny.MainDispatcherRule
import com.alexiscrack3.spinny.models.Player
import com.google.common.truth.Truth.assertThat
import io.github.serpro69.kfaker.faker
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito.mock
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.doNothing
import org.mockito.kotlin.eq
import org.mockito.kotlin.whenever

class LoginViewModelTest {
    private val faker = faker { }

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `signIn returns player when result is successful`() = runTest {
        val email = faker.internet.email()
        val password = faker.random.randomString()
        val player = Player(
            id = faker.random.nextInt(),
            firstName = faker.name.firstName(),
            lastName = faker.name.lastName(),
            email = email
        )
        val loginRepository = mock<LoginRepository>()
        val captor = argumentCaptor<(Result<Player>) -> Unit>()
        doNothing().whenever(loginRepository).signIn(eq(email), eq(password), captor.capture())
        val loginViewModel = LoginViewModel(loginRepository)

        loginViewModel.signIn(email, password)
        val callback = captor.firstValue
        callback(Result.success(player))

        val actual = loginViewModel.playerState.value
        assertThat(actual).isEqualTo(player)
    }

    @Test
    fun `signIn returns null when result is not successful`() = runTest {
        val email = faker.internet.email()
        val password = faker.random.randomString()
        val loginRepository = mock<LoginRepository>()
        val captor = argumentCaptor<(Result<Player>) -> Unit>()
        doNothing().whenever(loginRepository).signIn(eq(email), eq(password), captor.capture())
        val loginViewModel = LoginViewModel(loginRepository)

        loginViewModel.signIn(email, password)
        val callback = captor.firstValue
        callback(Result.failure(Throwable()))

        val actual = loginViewModel.playerState.value
        assertThat(actual).isNull()
    }
}
