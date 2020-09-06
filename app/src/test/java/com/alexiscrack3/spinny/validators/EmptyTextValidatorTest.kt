package com.alexiscrack3.spinny.validators

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class EmptyTextValidatorTest {

    @Test
    fun `validate should return valid when the string is not empty`() {
        val testObject = EmptyTextValidator()

        val result = testObject.validate("Not Empty!")

        assertThat(result).isInstanceOf(ValidatorResult.Success::class.java)
    }

    @Test
    fun `validate should return invalid when the string is empty`() {
        val testObject = EmptyTextValidator()

        val result = testObject.validate("")

        assertThat(result).isInstanceOf(ValidatorResult.Failure::class.java)
    }

    @Test
    fun `validate should return invalid when the string is blank`() {
        val testObject = EmptyTextValidator()

        val result = testObject.validate("   ")

        assertThat(result).isInstanceOf(ValidatorResult.Failure::class.java)
    }
}
