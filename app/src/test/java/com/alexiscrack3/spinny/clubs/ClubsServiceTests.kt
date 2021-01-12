package com.alexiscrack3.spinny.clubs

import com.alexiscrack3.spinny.api.ServicesFactory
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
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

class ClubsServiceTests {
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
    fun `getClubs should return clubs response`() = runBlocking {
        val id = "123"
        val name = "name"
        val imageUrl = "https://placeimg.com/640/480/null?53682"
        val now = Date()
        val offsetDateTime = now.toInstant().atOffset(ZoneOffset.UTC)
        val createdAt = offsetDateTime.format(DateTimeFormatter.ISO_INSTANT)
        val updatedAt = offsetDateTime.format(DateTimeFormatter.ISO_INSTANT)
        val membersCount = 3
        val jsonData = """
        {
            "data": [{
                "members": [
                    "5fe6d74de8be0fef6c0ac7aa",
                    "5fe6d74de8be0fef6c0ac7ab",
                    "5fe6d74de8be0fef6c0ac7ac"
                ],
                "_id": "$id",
                "name": "$name",
                "image_url": "$imageUrl",
                "created_at": "$createdAt",
                "updated_at": "$updatedAt",
                "members_count": $membersCount,
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
        val testObject = ServicesFactory(
            authTokenInterceptor = interceptor,
            baseUrl = baseUrl.toString()
        ).createService(ClubsService::class.java)

        val actual = testObject.getClubs()

        val data = actual.data.first()
        assertThat(data.id).isEqualTo(id)
        assertThat(data.name).isEqualTo(name)
        assertThat(data.imageUrl).isEqualTo(imageUrl)
        assertThat(data.createdAt).isEqualTo(now)
        assertThat(data.updatedAt).isEqualTo(now)
        assertThat(data.membersCount).isEqualTo(membersCount)
        assertThat(actual.errors).isEmpty()
    }

    @Test
    fun `getClubById should return club response`() = runBlocking {
        val id = "123"
        val name = "name"
        val imageUrl = "https://placeimg.com/640/480/null?53682"
        val now = Date()
        val offsetDateTime = now.toInstant().atOffset(ZoneOffset.UTC)
        val createdAt = offsetDateTime.format(DateTimeFormatter.ISO_INSTANT)
        val updatedAt = offsetDateTime.format(DateTimeFormatter.ISO_INSTANT)
        val jsonData = """
        {
            "data": {
                 "members": [{
                    "rating": 1000,
                    "_id": "5fe6d74de8be0fef6c0ac7aa",
                    "first_name": "Yadira",
                    "last_name": "O'Reilly",
                    "email": "florencio37@mailinator.com",
                    "created_at": "2020-12-26T06:25:17.051Z",
                    "__v": 0
                }],
                "_id": "$id",
                "name": "$name",
                "image_url": "$imageUrl",
                "created_at": "$createdAt",
                "updated_at": "$updatedAt",
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
        val testObject = ServicesFactory(
            authTokenInterceptor = interceptor,
            baseUrl = baseUrl.toString()
        ).createService(ClubsService::class.java)

        val actual = testObject.getClubById(id)

        val data = actual.data
        assertThat(data.id).isEqualTo(id)
        assertThat(data.name).isEqualTo(name)
        assertThat(data.imageUrl).isEqualTo(imageUrl)
        assertThat(data.createdAt).isEqualTo(now)
        assertThat(data.updatedAt).isEqualTo(now)
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
        val testObject = ServicesFactory(
            authTokenInterceptor = interceptor,
            baseUrl = baseUrl.toString()
        ).createService(ClubsService::class.java)

        val actual = testObject.createClub().blockingGet()

        val data = actual.data
        assertThat(data.id).isEqualTo(id)
        assertThat(data.name).isEqualTo(name)
        assertThat(data.createdAt).isEqualTo(now)
        assertThat(actual.errors).isEmpty()
    }
}
