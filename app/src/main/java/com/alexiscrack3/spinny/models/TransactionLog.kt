package com.alexiscrack3.spinny.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.OffsetDateTime

@Entity(tableName = "transaction_logs")
data class TransactionLog(
    @PrimaryKey
    @ColumnInfo(name = "entity_name")
    val entityName: String,
    @ColumnInfo(name = "created_at")
    val createdAt: OffsetDateTime = OffsetDateTime.now(),
    @ColumnInfo(name = "updated_at")
    val updatedAt: OffsetDateTime = OffsetDateTime.now()
)
