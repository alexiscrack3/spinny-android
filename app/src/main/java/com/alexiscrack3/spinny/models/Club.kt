package com.alexiscrack3.spinny.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "clubs")
data class Club(
    @PrimaryKey @ColumnInfo
    val id: String,
    val name: String
)
