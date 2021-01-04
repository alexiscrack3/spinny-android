package com.alexiscrack3.spinny.clubs

import com.alexiscrack3.spinny.api.ApiResponse
import com.alexiscrack3.spinny.api.ClubResponse
import com.alexiscrack3.spinny.api.ClubsResponse
import com.alexiscrack3.spinny.db.TransactionLogsDao
import com.alexiscrack3.spinny.models.Club
import com.alexiscrack3.spinny.models.Player
import com.alexiscrack3.spinny.models.TransactionLog
import com.alexiscrack3.spinny.utils.test
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Test
import java.time.OffsetDateTime

class ClubsRepositoryTest {

    @Test
    fun `clubs should be retrieved from service when data is out of date`() {
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
            on { this.getClubs() } doReturn Single.just(ApiResponse(arrayOf(clubsResponse)))
        }
        val clubsDao = mock<ClubsDao> {
            on { this.insertClubs(clubs) } doReturn Completable.complete()
        }
        val offsetDateTime = OffsetDateTime.now().minusYears(1)
        val transactionLog = TransactionLog(
            entityName = "clubs",
            createdAt = offsetDateTime,
            updatedAt = offsetDateTime
        )
        val transactionLogsDao = mock<TransactionLogsDao> {
            on { this.getTransactionLogByEntityName("clubs") } doReturn Single.just(transactionLog)
            on { this.updateTransactionLog(any()) } doReturn Single.just(1)
        }
        val testObject = ClubsRepository(clubsService, clubsDao, transactionLogsDao)

        testObject.getClubs()
            .test()
            .assertValue(clubs)
    }

    @Test
    fun `clubs should be stored in database after data is retrieved from service`() {
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
            on { this.getClubs() } doReturn Single.just(ApiResponse(arrayOf(clubsResponse)))
        }
        val clubsDao = mock<ClubsDao>()
        val offsetDateTime = OffsetDateTime.now().minusYears(1)
        val transactionLog = TransactionLog(
            entityName = "clubs",
            createdAt = offsetDateTime,
            updatedAt = offsetDateTime
        )
        val transactionLogsDao = mock<TransactionLogsDao> {
            on { this.getTransactionLogByEntityName("clubs") } doReturn Single.just(transactionLog)
        }
        val testObject = ClubsRepository(clubsService, clubsDao, transactionLogsDao)

        testObject.getClubs().test()

        verify(clubsDao).insertClubs(clubs)
    }

    @Test
    fun `clubs should be retrieved from database when data is not out of date`() {
        val club = Club.test()
        val clubs = listOf(club)
        val clubsService = mock<ClubsService>()
        val clubsDao = mock<ClubsDao> {
            on { this.getClubs() } doReturn Single.just(clubs)
        }
        val offsetDateTime = OffsetDateTime.now().plusYears(1)
        val transactionLog = TransactionLog(
            entityName = "clubs",
            createdAt = offsetDateTime,
            updatedAt = offsetDateTime
        )
        val transactionLogsDao = mock<TransactionLogsDao> {
            on { this.getTransactionLogByEntityName("clubs") } doReturn Single.just(transactionLog)
        }
        val testObject = ClubsRepository(clubsService, clubsDao, transactionLogsDao)

        testObject.getClubs()
            .test()
            .assertValue(clubs)
    }

    @Test
    fun `transaction log should be stored in database if entity does not exist`() {
        val transactionLogsDao = mock<TransactionLogsDao> {
            on { this.getTransactionLogByEntityName("clubs") } doReturn Single.error(Throwable())
        }
        val testObject = ClubsRepository(mock(), mock(), transactionLogsDao)

        testObject.getClubs().test()

        verify(transactionLogsDao).insertTransactionLog(any())
    }

    @Test
    fun `transaction log should be updated in database after data is retrieved from service`() {
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
        val club = Club(
            id = id,
            name = name,
            imageUrl = url,
            membersCount = membersCount
        )
        val clubs = listOf(club)
        val clubsService = mock<ClubsService> {
            on { this.getClubs() } doReturn Single.just(ApiResponse(arrayOf(clubsResponse)))
        }
        val clubsDao = mock<ClubsDao> {
            on { this.insertClubs(clubs) } doReturn Completable.complete()
        }
        val offsetDateTime = OffsetDateTime.now().minusYears(1)
        val transactionLog = TransactionLog(
            entityName = "clubs",
            createdAt = offsetDateTime,
            updatedAt = offsetDateTime
        )
        val transactionLogsDao = mock<TransactionLogsDao> {
            on { this.getTransactionLogByEntityName("clubs") } doReturn Single.just(transactionLog)
        }
        val testObject = ClubsRepository(clubsService, clubsDao, transactionLogsDao)

        testObject.getClubs().test()

        verify(transactionLogsDao).updateTransactionLog(any())
    }

    @Test
    fun `club should be retrieved from service`() {
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
            on { this.getClubById(id) } doReturn Single.just(ApiResponse(clubResponse))
        }
        val testObject = ClubsRepository(clubsService, mock(), mock())

        testObject.getClubById(id)
            .test()
            .assertValue(club)
    }
}
