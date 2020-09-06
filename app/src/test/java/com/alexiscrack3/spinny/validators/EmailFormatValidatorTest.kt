package com.alexiscrack3.spinny.validators

import com.alexiscrack3.spinny.SpinnyTest
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class EmailFormatValidatorTest : SpinnyTest() {

    @Test
    fun `validate should return valid when the string is a valid email`() {
        val testObject = EmailFormatValidator()

        val result = testObject.validate("example@mail.com")

        assertThat(result).isInstanceOf(ValidatorResult.Success::class.java)
    }

    @Test
    fun `validate should return invalid when the string is not a valid email`() {
        val testObject = EmailFormatValidator()

        val result = testObject.validate("Not An Email!")

        assertThat(result).isInstanceOf(ValidatorResult.Failure::class.java)
    }
}
