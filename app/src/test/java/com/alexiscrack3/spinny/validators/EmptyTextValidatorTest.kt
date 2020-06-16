package com.alexiscrack3.spinny.validators

import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class EmptyTextValidatorTest {

    @Test
    fun `validate should return valid when the string is not empty`() {
        val testObject = EmptyTextValidator("Not Empty!")

        val result = testObject.validate("Not Empty!")

        assertThat(result, instanceOf(ValidatorResult.VALID::class.java))
    }

    @Test
    fun `validate should return invalid when the string is empty`() {
        val testObject = EmptyTextValidator("Not Empty!")

        val result = testObject.validate("")

        assertThat(result, instanceOf(ValidatorResult.INVALID::class.java))
    }

    @Test
    fun `validate should return invalid when the string is blank`() {
        val testObject = EmptyTextValidator("Not Empty!")

        val result = testObject.validate("   ")

        assertThat(result, instanceOf(ValidatorResult.INVALID::class.java))
    }
}
