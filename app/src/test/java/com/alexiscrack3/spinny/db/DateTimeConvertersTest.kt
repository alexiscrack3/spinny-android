package com.alexiscrack3.spinny.db

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import java.lang.Exception
import java.lang.RuntimeException
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeParseException

class DateTimeConvertersTest {

    @Test
    fun `fromOffsetDateTime should return date time with offset when value is valid`() {
        val expected = OffsetDateTime.of(
            2011,
            12,
            3,
            10,
            15,
            30,
            0,
            ZoneOffset.ofHours(1)
        )
        val testObject = DateTimeConverters()

        val actual = testObject.fromOffsetDateTime("2011-12-03T10:15:30+01:00")

        assertThat(actual).isEqualTo(expected)
    }

    @Test(expected = DateTimeParseException::class)
    fun `fromOffsetDateTime should throw a date time parse exception when value is not valid`() {
        val testObject = DateTimeConverters()

        testObject.fromOffsetDateTime("")
    }

    @Test
    fun `fromOffsetDateTime should return null when value is null`() {
        val testObject = DateTimeConverters()

        val actual = testObject.fromOffsetDateTime(null)

        assertThat(actual).isNull()
    }

    @Test
    fun `toOffsetDateTime should return date time string with offset when date time is not null`() {
        val dateTime = OffsetDateTime.of(
            2011,
            12,
            3,
            10,
            15,
            30,
            0,
            ZoneOffset.ofHours(1)
        )
        val testObject = DateTimeConverters()

        val actual = testObject.toOffsetDateTime(dateTime)

        assertThat(actual).isEqualTo("2011-12-03T10:15:30+01:00")
    }

    @Test
    fun `toOffsetDateTime should return null when date time is null`() {
        val testObject = DateTimeConverters()

        val actual = testObject.toOffsetDateTime(null)

        assertThat(actual).isNull()
    }
}
