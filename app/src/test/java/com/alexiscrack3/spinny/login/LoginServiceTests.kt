package com.alexiscrack3.spinny.login

import com.alexiscrack3.spinny.api.ServicesFactory
import com.alexiscrack3.spinny.api.SignInRequest
import com.alexiscrack3.spinny.api.SignUpRequest
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.mock
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.net.HttpURLConnection
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

class LoginServiceTests {
    private var mockWebServer = MockWebServer()
    private val interceptor = object : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            return chain.proceed(chain.request())
        }
    }

    @Before
    fun setUp() {
        mockWebServer.start()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `signIn should return sign in response`() {
        val rating = 100
        val playerId = "123"
        val email = "foo@spinny.io"
        val now = Date()
        val offsetDateTime = now.toInstant().atOffset(ZoneOffset.UTC)
        val createdAt = offsetDateTime.format(DateTimeFormatter.ISO_INSTANT)
        val accessToken = "accessToken"
        val jsonData = """
        {
            "data": {
                "user": {
                    "rating": ${rating},
                    "_id": "$playerId",
                    "email": "$email",
                    "created_at": "$createdAt",
                    "__v": 0
                },
                "token": "$accessToken"
            },
            "errors": []
        }
        """.trimIndent()
        val mockResponse = MockResponse()
            .addHeader("Content-Type", "application/json; charset=utf-8")
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(jsonData)
        mockWebServer.enqueue(mockResponse)
        val baseUrl = mockWebServer.url("/")
        val testObject = ServicesFactory(interceptor, baseUrl.toString()).createService(LoginService::class.java)

        val actual = testObject.signIn(SignInRequest(email, "password")).blockingGet()

        val user = actual.data.user
        assertThat(user.id).isEqualTo(playerId)
        assertThat(user.createdAt).isEqualTo(now)
        assertThat(user.email).isEqualTo(email)
        assertThat(user.rating).isEqualTo(rating)
        assertThat(actual.data.token).isEqualTo(accessToken)
        assertThat(actual.errors).isEmpty()
    }

    @Test
    fun `signUp should return sign up response`() {
        val rating = 100
        val playerId = "123"
        val email = "foo@spinny.io"
        val now = Date()
        val offsetDateTime = now.toInstant().atOffset(ZoneOffset.UTC)
        val createdAt = offsetDateTime.format(DateTimeFormatter.ISO_INSTANT)
        val accessToken = "accessToken"
        val jsonData = """
        {
            "data": {
                "user": {
                    "rating": ${rating},
                    "_id": "$playerId",
                    "email": "$email",
                    "created_at": "$createdAt",
                    "__v": 0
                },
                "token": "$accessToken"
            },
            "errors": []
        }
        """.trimIndent()
        val mockResponse = MockResponse()
            .addHeader("Content-Type", "application/json; charset=utf-8")
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(jsonData)
        val dispatcher = object : Dispatcher() {
            @Throws(InterruptedException::class)
            override fun dispatch(request: RecordedRequest): MockResponse {
                return mockResponse
            }
        }
        mockWebServer.dispatcher = dispatcher
        val baseUrl = mockWebServer.url("/")
        val testObject = ServicesFactory(interceptor, baseUrl.toString()).createService(LoginService::class.java)

        val actual = testObject.signUp(SignUpRequest(email, "password")).blockingGet()

        val user = actual.data.user
        assertThat(user.id).isEqualTo(playerId)
        assertThat(user.createdAt).isEqualTo(now)
        assertThat(user.email).isEqualTo(email)
        assertThat(user.rating).isEqualTo(rating)
        assertThat(actual.data.token).isEqualTo(accessToken)
        assertThat(actual.errors).isEmpty()
    }
}
