package com.alexiscrack3.spinny.login

import com.alexiscrack3.spinny.api.ApiDocument
import com.alexiscrack3.spinny.api.LoginRequest
import com.alexiscrack3.spinny.api.LoginService
import com.alexiscrack3.spinny.api.models.PlayerAccountApiModel
import com.alexiscrack3.spinny.api.models.PlayerApiModel
import com.alexiscrack3.spinny.models.Player
import io.github.serpro69.kfaker.faker
import okhttp3.ResponseBody

import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.kotlin.whenever
import org.mockito.stubbing.Answer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginRepositoryTest {
    private val faker = faker {}

    @Test
    fun `signIn returns result with player when callback succeeds`() {
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
        `when`(call.enqueue(any())).thenAnswer(answer)
        val loginService = mock<LoginService>()
        whenever(loginService.signIn(loginRequest)).thenReturn(call)
        val loginRepository = LoginRepository(loginService)
        val callback = mock<(Result<Player>) -> Unit>()

        loginRepository.signIn(email, password, callback)

        verify(callback).invoke(expected)
    }

    @Test
    fun `signIn returns result with exception when callback fails`() {
        val email = faker.internet.email()
        val password = faker.random.randomString(10)
        val loginRequest = LoginRequest(
            player = PlayerAccountApiModel(
                email = email,
                password = password
            )
        )
        val expected = Result.failure<Player>(Throwable("Something went wrong"))
        val mock = mock<ResponseBody>()
        val response = Response.error<ApiDocument<PlayerApiModel>?>(500, mock)
        val call = mock<Call<ApiDocument<PlayerApiModel>?>>()
        val answer = Answer {
            val arg = it.getArgument<Callback<ApiDocument<PlayerApiModel>?>>(0)
            arg.onResponse(call, response)
        }
        `when`(call.enqueue(any())).thenAnswer(answer)
        val loginService = mock<LoginService>()
        whenever(loginService.signIn(loginRequest)).thenReturn(call)
        val loginRepository = LoginRepository(loginService)
        val callback = mock<(Result<Player>) -> Unit>()

        val captor = ArgumentCaptor.forClass<String, Unit>()
        loginRepository.signIn(email, password, callback)

        verify(callback).invoke(captor.capture())

        val blah = captor.value


    }
}

//inline fun <reified T : Any> argumentCaptor() = ArgumentCaptor.forClass(T::class.java)