package com.alexiscrack3.spinny.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alexiscrack3.spinny.clubs.ClubsDao
import com.alexiscrack3.spinny.models.Club

@Database(
    version = 1,
    entities = [(Club::class)],
    exportSchema = false
)
abstract class SpinnyDatabase : RoomDatabase() {

    abstract fun clubsDao(): ClubsDao
}
