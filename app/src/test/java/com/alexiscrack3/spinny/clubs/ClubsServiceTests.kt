package com.alexiscrack3.spinny.clubs

import com.alexiscrack3.spinny.api.ServicesFactory
import com.google.common.truth.Truth.assertThat
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.net.HttpURLConnection
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

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
        val now = Date()
        val offsetDateTime = now.toInstant().atOffset(ZoneOffset.UTC)
        val createdAt = offsetDateTime.format(DateTimeFormatter.ISO_INSTANT)
        val jsonData = """
        {
            "data": [{
                "members": [],
                "_id": "$id",
                "name": "$name",
                "created_at": "$createdAt",
                "__v": 0
            }],
            "errors": []
        }
        """.trimIndent()
        val mockResponse = MockResponse()
            .addHeader("Content-Type", "application/json; charset=utf-8")
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(jsonData)
        mockWebServer.enqueue(mockResponse)
        val baseUrl = mockWebServer.url("/")
        val testObject = ServicesFactory(baseUrl.toString()).createService(ClubsService::class.java)

        val actual = testObject.getClubs().blockingGet()

        val data = actual.data.first()
        assertThat(data.id).isEqualTo(id)
        assertThat(data.name).isEqualTo(name)
        assertThat(data.createdAt).isEqualTo(now)
        assertThat(actual.errors).isEmpty()
    }

    @Test
    fun `getClubById should return club response`() {
        val id = "123"
        val name = "name"
        val now = Date()
        val offsetDateTime = now.toInstant().atOffset(ZoneOffset.UTC)
        val createdAt = offsetDateTime.format(DateTimeFormatter.ISO_INSTANT)
        val jsonData = """
        {
            "data": {
                "members": [],
                "_id": "$id",
                "name": "$name",
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
        val testObject = ServicesFactory(baseUrl.toString()).createService(ClubsService::class.java)

        val actual = testObject.getClubById(id).blockingGet()

        val data = actual.data
        assertThat(data.id).isEqualTo(id)
        assertThat(data.name).isEqualTo(name)
        assertThat(data.createdAt).isEqualTo(now)
        assertThat(actual.errors).isEmpty()
    }

    @Test
    fun `createClub should return club response`() {
        val id = "123"
        val name = "name"
        val now = Date()
        val offsetDateTime = now.toInstant().atOffset(ZoneOffset.UTC)
        val createdAt = offsetDateTime.format(DateTimeFormatter.ISO_INSTANT)
        val jsonData = """
        {
            "data": {
                "members": [],
                "_id": "$id",
                "name": "$name",
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
        val testObject = ServicesFactory(baseUrl.toString()).createService(ClubsService::class.java)

        val actual = testObject.createClub().blockingGet()

        val data = actual.data
        assertThat(data.id).isEqualTo(id)
        assertThat(data.name).isEqualTo(name)
        assertThat(data.createdAt).isEqualTo(now)
        assertThat(actual.errors).isEmpty()
    }
}
