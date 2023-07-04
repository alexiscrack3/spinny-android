package com.alexiscrack3.spinny.login

import com.alexiscrack3.spinny.api.ApiDocument
import com.alexiscrack3.spinny.api.LoginRequest
import com.alexiscrack3.spinny.api.LoginService
import com.alexiscrack3.spinny.api.models.PlayerAccountApiModel
import com.alexiscrack3.spinny.api.models.PlayerApiModel
import com.alexiscrack3.spinny.models.Player
import com.google.common.truth.Truth.assertThat
import io.github.serpro69.kfaker.faker
import okhttp3.ResponseBody
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.whenever
import org.mockito.stubbing.Answer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginRepositoryTest {
    private val faker = faker {}

    @Test
    fun `signIn returns result with player when response is successful and data is not null`() {
        val email = faker.internet.email()
        val password = faker.random.randomString(10)
        val loginRequest = LoginRequest(
            player = PlayerAccountApiModel(
                email = email,
                password = password
            )
        )
        val playerApiModel = PlayerApiModel(
            id = faker.random.nextInt(),
            firstName = faker.name.firstName(),
            lastName = faker.name.lastName(),
            email = email
        )
        val player = Player(
            id = playerApiModel.id,
            firstName = playerApiModel.firstName,
            lastName = playerApiModel.lastName,
            email = playerApiModel.email
        )
        val expected = Result.success(player)
        val apiDocument = ApiDocument(playerApiModel)
        val response = Response.success(apiDocument)
        val call = mock<Call<ApiDocument<PlayerApiModel>?>>()
        val answer = Answer {
            val arg = it.getArgument<Callback<ApiDocument<PlayerApiModel>?>>(0)
            arg.onResponse(call, response)
        }
        whenever(call.enqueue(any())).thenAnswer(answer)
        val loginService = mock<LoginService>()
        whenever(loginService.signIn(loginRequest)).thenReturn(call)
        val loginRepository = LoginRepository(loginService)
        val callback = mock<(Result<Player>) -> Unit>()

        loginRepository.signIn(email, password, callback)

        verify(callback).invoke(expected)
    }

    @Test
    fun `signIn returns result with player when response is successful and data is null`() {
        val email = faker.internet.email()
        val password = faker.random.randomString(10)
        val loginRequest = LoginRequest(
            player = PlayerAccountApiModel(
                email = email,
                password = password
            )
        )
        val playerApiModel = PlayerApiModel(
            id = faker.random.nextInt(),
            firstName = faker.name.firstName(),
            lastName = faker.name.lastName(),
            email = email
        )
        val player = Player(
            id = playerApiModel.id,
            firstName = playerApiModel.firstName,
            lastName = playerApiModel.lastName,
            email = playerApiModel.email
        )
        val expected = Result.success(player)
        val apiDocument: ApiDocument<PlayerApiModel> = ApiDocument(null)
        val response = Response.success(apiDocument)
        val call = mock<Call<ApiDocument<PlayerApiModel>?>>()
        val answer = Answer {
            val arg = it.getArgument<Callback<ApiDocument<PlayerApiModel>>>(0)
            arg.onResponse(call, response)
        }
        whenever(call.enqueue(any())).thenAnswer(answer)
        val loginService = mock<LoginService>()
        whenever(loginService.signIn(loginRequest)).thenReturn(call)
        val loginRepository = LoginRepository(loginService)
        val callback = mock<(Result<Player>) -> Unit>()

        loginRepository.signIn(email, password, callback)

        argumentCaptor<Result<Player>>().apply {
            verify(callback).invoke(capture())
            assertThat(firstValue.isFailure).isTrue()
        }
    }

    @Test
    fun `signIn returns result with exception when response is not successful`() {
        val email = faker.internet.email()
        val password = faker.random.randomString(10)
        val loginRequest = LoginRequest(
            player = PlayerAccountApiModel(
                email = email,
                password = password
            )
        )
        val mock = mock<ResponseBody>()
        val response = Response.error<ApiDocument<PlayerApiModel>?>(500, mock)
        val call = mock<Call<ApiDocument<PlayerApiModel>?>>()
        val answer = Answer {
            val arg = it.getArgument<Callback<ApiDocument<PlayerApiModel>?>>(0)
            arg.onResponse(call, response)
        }
        whenever(call.enqueue(any())).thenAnswer(answer)
        val loginService = mock<LoginService>()
        whenever(loginService.signIn(loginRequest)).thenReturn(call)
        val loginRepository = LoginRepository(loginService)
        val callback = mock<(Result<Player>) -> Unit>()

        loginRepository.signIn(email, password, callback)

        argumentCaptor<Result<Player>>().apply {
            verify(callback).invoke(capture())
            assertThat(firstValue.isFailure).isTrue()
        }
    }

    @Test
    fun `signIn returns result with exception when response fails`() {
        val email = faker.internet.email()
        val password = faker.random.randomString(10)
        val loginRequest = LoginRequest(
            player = PlayerAccountApiModel(
                email = email,
                password = password
            )
        )
        val throwable = Throwable("Some went wrong")
        val call = mock<Call<ApiDocument<PlayerApiModel>?>>()
        val answer = Answer {
            val arg = it.getArgument<Callback<ApiDocument<PlayerApiModel>?>>(0)
            arg.onFailure(call, throwable)
        }
        whenever(call.enqueue(any())).thenAnswer(answer)
        val loginService = mock<LoginService>()
        whenever(loginService.signIn(loginRequest)).thenReturn(call)
        val loginRepository = LoginRepository(loginService)
        val callback = mock<(Result<Player>) -> Unit>()

        loginRepository.signIn(email, password, callback)

        argumentCaptor<Result<Player>>().apply {
            verify(callback).invoke(capture())
            assertThat(firstValue.isFailure).isTrue()
        }
    }
}
