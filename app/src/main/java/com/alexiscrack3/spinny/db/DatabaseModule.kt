package com.alexiscrack3.spinny.db

import androidx.room.Room
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
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
