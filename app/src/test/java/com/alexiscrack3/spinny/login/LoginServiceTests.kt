package com.alexiscrack3.spinny.login

import com.alexiscrack3.spinny.api.ServicesFactory
import com.alexiscrack3.spinny.api.SignInRequest
import com.alexiscrack3.spinny.api.SignUpRequest
import com.google.common.truth.Truth.assertThat
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
        val testObject = ServicesFactory(baseUrl.toString()).createService(LoginService::class.java)

        val actual = testObject.signIn(SignInRequest(email, "password")).blockingGet()

        val data = actual.data
        assertThat(data.user.id).isEqualTo(playerId)
        assertThat(data.user.createdAt).isEqualTo(now)
        assertThat(data.user.email).isEqualTo(email)
        assertThat(data.user.rating).isEqualTo(rating)
        assertThat(data.token).isEqualTo(accessToken)
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
        val testObject = ServicesFactory(baseUrl.toString()).createService(LoginService::class.java)

        val actual = testObject.signUp(SignUpRequest(email, "password")).blockingGet()

        val data = actual.data
        assertThat(data.user.id).isEqualTo(playerId)
        assertThat(data.user.createdAt).isEqualTo(now)
        assertThat(data.user.email).isEqualTo(email)
        assertThat(data.user.rating).isEqualTo(rating)
        assertThat(data.token).isEqualTo(accessToken)
    }
}
