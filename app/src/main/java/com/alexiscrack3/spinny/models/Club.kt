package com.alexiscrack3.spinny.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "clubs")
data class Club(
    @PrimaryKey
    val id: String,
    val name: String,
    val membersCount: Int
) {
    @Ignore
    var members: List<String> = emptyList()

    companion object
}
