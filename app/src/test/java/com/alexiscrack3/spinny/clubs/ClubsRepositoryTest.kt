package com.alexiscrack3.spinny.clubs

import com.alexiscrack3.spinny.api.ApiResponse
import com.alexiscrack3.spinny.api.ClubResponse
import com.alexiscrack3.spinny.api.ClubsResponse
import com.alexiscrack3.spinny.db.TransactionLogsDao
import com.alexiscrack3.spinny.models.Club
import com.alexiscrack3.spinny.models.Player
import com.alexiscrack3.spinny.models.TransactionLog
import com.alexiscrack3.spinny.utils.test
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.time.OffsetDateTime

class ClubsRepositoryTest {

    @Test
    fun `clubs should be retrieved from service when data is out of date`() = runBlocking {
        val id = "1"
        val name = "a"
        val url = "url"
        val membersCount = 0
        val clubsResponse = ClubsResponse.test(
            id = id,
            name = name,
            imageUrl = url,
            membersCount = membersCount
        )
        val club = Club.test(
            id = id,
            name = name,
            imageUrl = url,
            membersCount = membersCount
        )
        val clubs = listOf(club)
        val clubsService = mock<ClubsService> {
            onBlocking { this.getClubs() } doReturn ApiResponse(arrayOf(clubsResponse))
        }
        val clubsDao = mock<ClubsDao> {
            onBlocking { this.insertClubs(clubs) } doReturn Unit
        }
        val offsetDateTime = OffsetDateTime.now().minusYears(1)
        val transactionLog = TransactionLog(
            entityName = "clubs",
            createdAt = offsetDateTime,
            updatedAt = offsetDateTime
        )
        val transactionLogsDao = mock<TransactionLogsDao> {
            onBlocking { this.getTransactionLogByEntityName("clubs") } doReturn transactionLog
            onBlocking { this.updateTransactionLog(any()) } doReturn 1
        }
        val testObject = ClubsRepository(clubsService, clubsDao, transactionLogsDao)

        val actual = testObject.getClubs()

        assertThat(actual).isEqualTo(clubs)
    }

//    @Test
//    fun `clubs should be stored in database after data is retrieved from service`() = runBlocking {
//        val id = "1"
//        val name = "a"
//        val url = "url"
//        val membersCount = 0
//        val clubsResponse = ClubsResponse.test(
//            id = id,
//            name = name,
//            imageUrl = url,
//            membersCount = membersCount
//        )
//        val club = Club.test(
//            id = id,
//            name = name,
//            imageUrl = url,
//            membersCount = membersCount
//        )
//        val clubs = listOf(club)
//        val clubsService = mock<ClubsService> {
//            onBlocking { this.getClubs() } doReturn ApiResponse(arrayOf(clubsResponse))
//        }
//        val clubsDao = mock<ClubsDao>()
//        val offsetDateTime = OffsetDateTime.now().minusYears(1)
//        val transactionLog = TransactionLog(
//            entityName = "clubs",
//            createdAt = offsetDateTime,
//            updatedAt = offsetDateTime
//        )
//        val transactionLogsDao = mock<TransactionLogsDao> {
//            onBlocking { this.getTransactionLogByEntityName("clubs") } doReturn transactionLog
//        }
//        val testObject = ClubsRepository(clubsService, clubsDao, transactionLogsDao)
//
//        testObject.getClubs()
//
//        verify(clubsDao).insertClubs(clubs)
//    }

//    @Test
//    fun `clubs should be retrieved from database when data is not out of date`() = runBlocking {
//        val club = Club.test()
//        val clubs = listOf(club)
//        val clubsService = mock<ClubsService>()
//        val clubsDao = mock<ClubsDao> {
//            onBlocking { this.getClubs() } doReturn clubs
//        }
//        val offsetDateTime = OffsetDateTime.now().plusYears(1)
//        val transactionLog = TransactionLog(
//            entityName = "clubs",
//            createdAt = offsetDateTime,
//            updatedAt = offsetDateTime
//        )
//        val transactionLogsDao = mock<TransactionLogsDao> {
//            onBlocking { this.getTransactionLogByEntityName("clubs") } doReturn transactionLog
//        }
//        val testObject = ClubsRepository(clubsService, clubsDao, transactionLogsDao)
//
//        val actual = testObject.getClubs()
//
//        assertThat(actual).isEqualTo(clubs)
//    }

//    @Test
//    fun `transaction log should be stored in database if entity does not exist`() = runBlocking{
//        val transactionLogsDao = mock<TransactionLogsDao> {
//            onBlocking { this.getTransactionLogByEntityName("clubs") } doReturn Throwable()
//        }
//        val testObject = ClubsRepository(mock(), mock(), transactionLogsDao)
//
//        testObject.getClubs()
//
//        verify(transactionLogsDao).insertTransactionLog(any())
//    }

//    @Test
//    fun `transaction log should be updated in database after data is retrieved from service`() = runBlocking {
//        val id = "1"
//        val name = "a"
//        val url = "url"
//        val membersCount = 0
//        val clubsResponse = ClubsResponse.test(
//            id = id,
//            name = name,
//            imageUrl = url,
//            membersCount = membersCount
//        )
//        val club = Club(
//            id = id,
//            name = name,
//            imageUrl = url,
//            membersCount = membersCount
//        )
//        val clubs = listOf(club)
//        val clubsService = mock<ClubsService> {
//            onBlocking { this.getClubs() } doReturn ApiResponse(arrayOf(clubsResponse))
//        }
//        val clubsDao = mock<ClubsDao> {
//            onBlocking { this.insertClubs(clubs) } doReturn Unit
//        }
//        val offsetDateTime = OffsetDateTime.now().minusYears(1)
//        val transactionLog = TransactionLog(
//            entityName = "clubs",
//            createdAt = offsetDateTime,
//            updatedAt = offsetDateTime
//        )
//        val transactionLogsDao = mock<TransactionLogsDao> {
//            onBlocking { this.getTransactionLogByEntityName("clubs") } doReturn transactionLog
//        }
//        val testObject = ClubsRepository(clubsService, clubsDao, transactionLogsDao)
//
//        testObject.getClubs()
//
//        verify(transactionLogsDao).updateTransactionLog(any())
//    }

    @Test
    fun `club should be retrieved from service`() = runBlocking {
        val id = "1"
        val name = "a"
        val url = "url"
        val members = listOf(
            Player.test()
        )
        val membersCount = 0
        val clubResponse = ClubResponse.test(
            id = id,
            name = name,
            imageUrl = url,
            members = members,
            membersCount = membersCount
        )
        val club = Club.test(
            id = id,
            name = name,
            imageUrl = url,
            membersCount = membersCount
        ).apply {
            this.members = members
        }
        val clubsService = mock<ClubsService> {
            onBlocking { this.getClubById(id) } doReturn ApiResponse(clubResponse)
        }
        val testObject = ClubsRepository(clubsService, mock(), mock())

        val actual = testObject.getClubById(id)

        assertThat(actual).isEqualTo(club)
    }
}
