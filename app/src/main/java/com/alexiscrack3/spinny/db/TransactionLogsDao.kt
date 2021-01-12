package com.alexiscrack3.spinny.db

import androidx.room.*
import com.alexiscrack3.spinny.models.TransactionLog

@Dao
interface TransactionLogsDao {

    @Query("SELECT * FROM transaction_logs WHERE entity_name = :entityName")
    suspend fun getTransactionLogByEntityName(entityName: String): TransactionLog?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransactionLog(transactionLog: TransactionLog)

    @Update
    suspend fun updateTransactionLog(transactionLog: TransactionLog): Int
}
