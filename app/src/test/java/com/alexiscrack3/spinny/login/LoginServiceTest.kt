package com.alexiscrack3.spinny.login

import com.alexiscrack3.spinny.MockResponseFileReader
import com.alexiscrack3.spinny.api.LoginRequest
import com.alexiscrack3.spinny.api.LoginService
import com.alexiscrack3.spinny.api.models.PlayerAccountApiModel
import com.google.common.truth.Truth.assertThat
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

class LoginServiceTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var retrofit: Retrofit

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `sign in`() {
        val mockResponseFileReader = MockResponseFileReader("api/login_successful_response.json")
        val body = mockResponseFileReader.content
        val response = MockResponse()
            .addHeader("Content-Type", "application/json; charset=utf-8")
            .setHeader("Authorization", "Bearer token")
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(body)
        mockWebServer.enqueue(response)

        val loginService = retrofit.create(LoginService::class.java)

        val loginRequest = LoginRequest(
            player = PlayerAccountApiModel(
                email = "email@gmail.com",
                password = "123456"
            )
        )
        val actualResponse = loginService.signIn(loginRequest).execute()
        val apiDocument = actualResponse.body()
        val player = apiDocument?.data

        assertThat(player?.id).isEqualTo(1)
        assertThat(player?.firstName).isEqualTo("Foo")
        assertThat(player?.lastName).isEqualTo("Bar")
        assertThat(player?.email).isEqualTo("email@gmail.com")
    }
}
