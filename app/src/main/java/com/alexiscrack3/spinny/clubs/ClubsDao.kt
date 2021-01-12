package com.alexiscrack3.spinny.clubs

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alexiscrack3.spinny.models.Club

@Dao
interface ClubsDao {

    @Query("SELECT * FROM clubs")
    suspend fun getClubs(): List<Club>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertClubs(clubs: List<Club>)
}
