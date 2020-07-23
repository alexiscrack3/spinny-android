package com.alexiscrack3.spinny.clubs

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.alexiscrack3.spinny.models.Club

@Dao
interface ClubsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertClubs(clubs: List<Club>)
}
