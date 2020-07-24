package com.alexiscrack3.spinny.clubs

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alexiscrack3.spinny.models.Club
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface ClubsDao {

    @Query("SELECT * FROM clubs")
    fun getClubs(): Single<List<Club>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertClubs(clubs: List<Club>): Completable
}
