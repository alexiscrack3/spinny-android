package com.alexiscrack3.spinny.login

import com.alexiscrack3.spinny.api.ServicesFactory
import com.alexiscrack3.spinny.api.SignInRequest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.net.HttpURLConnection

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
        val playerId = "123"
        val email = "foo@spinny.io"
        val rating = 100
        val accessToken = "accessToken"
        val jsonData =
            "{\"data\":{\"user\":{\"_id\":\"${playerId}\",\"email\":\"${email}\",\"rating\":${rating}},\"token\":\"${accessToken}\"}}"
        val mockResponse = MockResponse()
            .addHeader("Content-Type", "application/json; charset=utf-8")
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(jsonData)
        mockWebServer.enqueue(mockResponse)

//        val dispatcher = object : Dispatcher() {
//            @Throws(InterruptedException::class)
//            override fun dispatch(request: RecordedRequest): MockResponse {
//                return mockResponse
//            }
//        }
//        mockWebServer.dispatcher = dispatcher
        val baseUrl = mockWebServer.url("/")
        val testObject = ServicesFactory(baseUrl.toString()).createService(LoginService::class.java)

        val actual = testObject.signIn(SignInRequest(email, "password")).blockingGet()
        val data = actual.data
        assertThat(data.user.id, equalTo(playerId))
        assertThat(data.user.email, equalTo(email))
        assertThat(data.user.rating, equalTo(rating))
        assertThat(data.token, equalTo(accessToken))
    }
}
