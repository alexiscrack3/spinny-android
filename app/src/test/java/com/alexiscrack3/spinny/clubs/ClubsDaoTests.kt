package com.alexiscrack3.spinny.clubs

import androidx.room.Room
import com.alexiscrack3.spinny.SpinnyTest
import com.alexiscrack3.spinny.db.SpinnyDatabase
import com.alexiscrack3.spinny.models.Club
import com.alexiscrack3.spinny.models.test
import org.junit.After
import org.junit.Before
import org.junit.Test

class ClubsDaoTests : SpinnyTest() {
    private lateinit var spinnyDatabase: SpinnyDatabase

    @Before
    override fun setUp() {
        super.setUp()
        spinnyDatabase = Room.inMemoryDatabaseBuilder(
            context,
            SpinnyDatabase::class.java
        ).allowMainThreadQueries().build()
    }

    @After
    override fun tearDown() {
        super.tearDown()
        spinnyDatabase.close()
    }

    @Test
    fun `clubs are inserted into database`() {
        val club = Club.test()
        val clubs = listOf(club)
        val clubsDao = spinnyDatabase.clubsDao()

        clubsDao.insertClubs(clubs).blockingAwait()

        clubsDao.getClubs()
            .test()
            .assertValue(clubs)
    }
}
