package com.alexiscrack3.spinny.db

import androidx.room.Room
import com.alexiscrack3.spinny.SpinnyTest
import com.alexiscrack3.spinny.models.TransactionLog
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.time.OffsetDateTime

class TransactionLogsDaoTest : SpinnyTest() {
    private lateinit var database: SpinnyDatabase
    private lateinit var testObject: TransactionLogsDao

    @Before
    override fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            context,
            SpinnyDatabase::class.java
        ).allowMainThreadQueries().build()

        testObject = database.transactionLogsDao()
    }

    @After
    override fun tearDown() {
        database.close()
    }

    @Test
    fun `transaction logs should be retrieved from database`() = runBlocking {
        val entityName = "entity"
        val dateTime = OffsetDateTime.now()
        val transactionLog = TransactionLog(
            entityName = entityName,
            createdAt = dateTime,
            updatedAt = dateTime
        )
        testObject.insertTransactionLog(transactionLog)

        val actual = testObject.getTransactionLogByEntityName(entityName)

        assertThat(actual).isEqualTo(transactionLog)
    }

    @Test
    fun `transaction logs should be updated in database`() = runBlocking {
        val entityName = "entity"
        val dateTime = OffsetDateTime.now()
        val transactionLog = TransactionLog(
            entityName = entityName,
            createdAt = dateTime,
            updatedAt = dateTime
        )
        testObject.insertTransactionLog(transactionLog)

        val updatedDateTime = dateTime.plusDays(1)
        val updatedTransactionLog = transactionLog.copy(updatedAt = updatedDateTime)
        testObject.updateTransactionLog(updatedTransactionLog)

        val actual = testObject.getTransactionLogByEntityName(entityName)

        assertThat(actual).isEqualTo(updatedTransactionLog)
    }
}
