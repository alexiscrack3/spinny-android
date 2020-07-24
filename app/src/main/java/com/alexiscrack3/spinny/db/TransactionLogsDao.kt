package com.alexiscrack3.spinny.db

import androidx.room.*
import com.alexiscrack3.spinny.models.TransactionLog
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface TransactionLogsDao {

    @Query("SELECT * FROM transaction_logs WHERE entity_name = :entityName")
    fun getTransactionLogByEntityName(entityName: String): Single<TransactionLog?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTransactionLog(transactionLog: TransactionLog): Completable

    @Update
    fun updateTransactionLog(transactionLog: TransactionLog): Single<Int>
}
