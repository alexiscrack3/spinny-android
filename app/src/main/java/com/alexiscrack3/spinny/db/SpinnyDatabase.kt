package com.alexiscrack3.spinny.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.alexiscrack3.spinny.clubs.ClubsDao
import com.alexiscrack3.spinny.models.Club
import com.alexiscrack3.spinny.models.TransactionLog

@Database(
    version = 1,
    entities = [Club::class, TransactionLog::class],
    exportSchema = false
)
@TypeConverters(DateTimeConverters::class)
abstract class SpinnyDatabase : RoomDatabase() {

    abstract fun clubsDao(): ClubsDao

    abstract fun transactionLogsDao(): TransactionLogsDao
}
