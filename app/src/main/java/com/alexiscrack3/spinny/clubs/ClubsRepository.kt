package com.alexiscrack3.spinny.clubs

import com.alexiscrack3.spinny.api.ApiResponse
import com.alexiscrack3.spinny.api.ClubResponse
import com.alexiscrack3.spinny.db.TransactionLogsDao
import com.alexiscrack3.spinny.models.Club
import com.alexiscrack3.spinny.models.TransactionLog
import io.reactivex.Single
import java.time.OffsetDateTime

class ClubsRepository(
    private val clubsService: ClubsService,
    private val clubsDao: ClubsDao,
    private val transactionLogsDao: TransactionLogsDao,
    private val clubsMapper: ClubsMapper = ClubsMapper()
) {

    suspend fun getClubs(): List<Club> {
        val response = clubsService.getClubs()
        return clubsMapper.map(response.data)
//        return transactionLogsDao.getTransactionLogByEntityName("clubs")
//            .onErrorResumeNext {
//                val transactionLog = TransactionLog("clubs")
//                transactionLogsDao.insertTransactionLog(transactionLog).andThen(Single.just(transactionLog))
//            }
//            .flatMap { transactionLog ->
//                if (shouldRefresh(transactionLog)) {
//                    clubsService.getClubs()
//                        .map { clubsMapper.map(it.data) }
//                        .flatMap { clubs ->
//                            clubsDao.insertClubs(clubs).andThen(Single.just(clubs))
//                        }
//                        .flatMap { clubs ->
//                            val transactionLogCopy = transactionLog.copy(updatedAt = OffsetDateTime.now())
//                            transactionLogsDao.updateTransactionLog(transactionLogCopy).flatMap {
//                                Single.just(clubs)
//                            }
//                        }
//                } else {
//                    clubsDao.getClubs()
//                }
//            }
    }

    suspend fun getClubById(id: String): Club {
        val response = clubsService.getClubById(id)
        return clubsMapper.map(response.data)
    }

    fun createClub(): Single<ApiResponse<ClubResponse>> {
        return clubsService.createClub()
    }

    private fun shouldRefresh(transactionLog: TransactionLog): Boolean {
        val now = OffsetDateTime.now().minusSeconds(20)
        return transactionLog.updatedAt.isBefore(now)
    }
}
