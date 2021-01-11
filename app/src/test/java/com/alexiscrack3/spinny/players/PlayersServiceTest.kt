package com.alexiscrack3.spinny.players

import com.alexiscrack3.spinny.api.ServicesFactory
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.net.HttpURLConnection
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

class PlayersServiceTest {
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
    fun `getPlayer should return player response`() = runBlocking {
        val rating = 100
        val playerId = "123"
        val email = "foo@spinny.io"
        val firstName = "foo"
        val lastName = "foo"
        val now = Date()
        val offsetDateTime = now.toInstant().atOffset(ZoneOffset.UTC)
        val createdAt = offsetDateTime.format(DateTimeFormatter.ISO_INSTANT)
        val jsonData = """
        {
            "data": {
                "rating": $rating,
                "_id": "$playerId",
                "first_name": "$firstName",
                "last_name": "$lastName",
                "email": "$email",
                "created_at": "$createdAt",
                "__v": 0
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
        val testObject = ServicesFactory(interceptor, baseUrl.toString()).createService(PlayersService::class.java)

        val actual = testObject.getPlayer()

        val player = actual.data
        assertThat(player.id).isEqualTo(playerId)
        assertThat(player.email).isEqualTo(email)
        assertThat(player.firstName).isEqualTo(firstName)
        assertThat(player.lastName).isEqualTo(lastName)
        assertThat(player.rating).isEqualTo(rating)
        assertThat(player.createdAt).isEqualTo(now)
        assertThat(actual.errors).isEmpty()
    }
}
