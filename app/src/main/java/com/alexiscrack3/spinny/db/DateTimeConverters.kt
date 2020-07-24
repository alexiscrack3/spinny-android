package com.alexiscrack3.spinny.db

import androidx.room.TypeConverter
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class DateTimeConverters {
    private val dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

    @TypeConverter
    fun fromOffsetDateTime(value: String?): OffsetDateTime? {
        return value?.let { dateTimeFormatter.parse(it, OffsetDateTime::from) }
    }

    @TypeConverter
    fun toOffsetDateTime(date: OffsetDateTime?): String? {
        return date?.format(dateTimeFormatter)
    }
}
