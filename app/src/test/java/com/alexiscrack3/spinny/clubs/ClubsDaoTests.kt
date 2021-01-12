package com.alexiscrack3.spinny.clubs

import androidx.room.Room
import com.alexiscrack3.spinny.SpinnyTest
import com.alexiscrack3.spinny.db.SpinnyDatabase
import com.alexiscrack3.spinny.models.Club
import com.alexiscrack3.spinny.utils.test
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class ClubsDaoTests : SpinnyTest() {
    private lateinit var database: SpinnyDatabase
    private lateinit var testObject: ClubsDao

    @Before
    override fun setUp() {
        super.setUp()
        database = Room.inMemoryDatabaseBuilder(
            context,
            SpinnyDatabase::class.java
        ).allowMainThreadQueries().build()

        testObject = database.clubsDao()
    }

    @After
    override fun tearDown() {
        super.tearDown()
        database.close()
    }

    @Test
    fun `clubs are inserted into database`() = runBlocking {
        val club = Club.test()
        val clubs = listOf(club)
        val clubsDao = database.clubsDao()
        clubsDao.insertClubs(clubs)

        val actual = clubsDao.getClubs()

        assertThat(actual).isEqualTo(clubs)
    }
}
