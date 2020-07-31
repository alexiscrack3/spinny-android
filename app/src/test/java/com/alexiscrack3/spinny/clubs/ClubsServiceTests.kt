package com.alexiscrack3.spinny.clubs

import com.alexiscrack3.spinny.api.ServicesFactory
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.net.HttpURLConnection

class ClubsServiceTests {
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
    fun `getClubs should return clubs response`() {
        val id = "123"
        val name = "name"
        val jsonData = """
        {
            "data": [{
                "members": [],
                "_id": "$id",
                "name": "$name",
                "created_at": "2020-07-18T22:20:51.574Z"
            }]
        }
            """.trimIndent()

        val dispatcher = object : Dispatcher() {
            @Throws(InterruptedException::class)
            override fun dispatch(request: RecordedRequest): MockResponse {
                return MockResponse()
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .setResponseCode(HttpURLConnection.HTTP_OK)
                    .setBody(jsonData)
            }
        }
        mockWebServer.dispatcher = dispatcher
        val baseUrl = mockWebServer.url("/")
        val testObject = ServicesFactory(baseUrl.toString()).createService(ClubsService::class.java)

        val actual = testObject.getClubs().blockingGet()
        val data = actual.data.first()
        assertThat(data.id, equalTo(id))
        assertThat(data.name, equalTo(name))
    }
    @Test
    fun `getClubById should return club response`() {
        val id = "123"
        val name = "name"
        val jsonData = """
        {
            "data": {
                "members": [],
                "_id": "$id",
                "name": "$name",
                "created_at": "2020-07-18T22:20:51.574Z"
            }
        }
            """.trimIndent()

        val dispatcher = object : Dispatcher() {
            @Throws(InterruptedException::class)
            override fun dispatch(request: RecordedRequest): MockResponse {
                return MockResponse()
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .setResponseCode(HttpURLConnection.HTTP_OK)
                    .setBody(jsonData)
            }
        }
        mockWebServer.dispatcher = dispatcher
        val baseUrl = mockWebServer.url("/")
        val testObject = ServicesFactory(baseUrl.toString()).createService(ClubsService::class.java)

        val actual = testObject.getClubById(id).blockingGet()
        val data = actual.data
        assertThat(data.id, equalTo(id))
        assertThat(data.name, equalTo(name))
    }
}
