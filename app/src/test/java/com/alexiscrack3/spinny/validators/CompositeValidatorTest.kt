package com.alexiscrack3.spinny.validators

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class CompositeValidatorTest {

    @Test
    fun `validate should return valid when there are no validator errors`() {
        val emptyTextValidator = mock<EmptyTextValidator>()
        val text = "text"
        whenever(emptyTextValidator.validate(text)).thenReturn(ValidatorResult.VALID)
        val emailFormatValidator = mock<EmailFormatValidator>()
        whenever(emailFormatValidator.validate(text)).thenReturn(ValidatorResult.VALID)
        val testObject = CompositeValidator(emptyTextValidator, emailFormatValidator)

        val actual = testObject.validate(text)

        assertThat(actual, instanceOf(ValidatorResult.VALID::class.java))
    }

    @Test
    fun `validate should return invalid when there is at least one validator error`() {
        val emptyTextValidator = mock<EmptyTextValidator>()
        val text = "text"
        whenever(emptyTextValidator.validate(text)).thenReturn(ValidatorResult.VALID)
        val emailFormatValidator = mock<EmailFormatValidator>()
        whenever(emailFormatValidator.validate(text)).thenReturn(ValidatorResult.INVALID)
        val testObject = CompositeValidator(emptyTextValidator, emailFormatValidator)

        val actual = testObject.validate(text)

        assertThat(actual, instanceOf(ValidatorResult.INVALID::class.java))
    }
}
