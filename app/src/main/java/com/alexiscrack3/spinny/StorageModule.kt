package com.alexiscrack3.spinny

import androidx.room.Room
import com.alexiscrack3.spinny.db.SpinnyDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val storageModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            SpinnyDatabase::class.java,
            "spinny_database"
        ).build()
    }
    factory {
        val spinnyDatabase = get<SpinnyDatabase>()
        spinnyDatabase.clubsDao()
    }
    factory {
        val spinnyDatabase = get<SpinnyDatabase>()
        spinnyDatabase.transactionLogsDao()
    }
}
