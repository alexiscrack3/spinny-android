package com.alexiscrack3.spinny.validators

import com.alexiscrack3.spinny.SpinnyTest
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Assert.assertThat
import org.junit.Assert.fail
import org.junit.Test

class EmailFormatValidatorTest : SpinnyTest() {

    @Test
    fun `validate should return valid when the string is a valid email`() {
        val testObject = EmailFormatValidator("")

        val result = testObject.validate("example@mail.com")

        assertThat(result, instanceOf(ValidatorResult.Valid::class.java))
    }

    @Test
    fun `validate should return invalid when the string is not a valid email`() {
        val testObject = EmailFormatValidator("%s is invalid")

        val result = testObject.validate("Not An Email!")

        assertThat(result, instanceOf(ValidatorResult.Invalid::class.java))

        when (result) {
            ValidatorResult.Invalid -> {
                when (ValidatorResult.Invalid.error) {

                    ValidatorError("%s is invalid") -> assert(true)
                    else -> fail()
                }
            }
            else -> fail()
        }
    }
}