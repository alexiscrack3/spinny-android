package com.alexiscrack3.spinny.validators

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Test

class CompositeValidatorTest {

    @Test
    fun `validate should return valid when there are no validator errors`() {
        val emptyTextValidator = mock<EmptyTextValidator>()
        val text = "text"
        whenever(emptyTextValidator.validate(text)).thenReturn(ValidatorResult.Valid)
        val emailFormatValidator = mock<EmailFormatValidator>()
        whenever(emailFormatValidator.validate(text)).thenReturn(ValidatorResult.Valid)
        val testObject = CompositeValidator(emptyTextValidator, emailFormatValidator)

        val actual = testObject.validate(text)

        assertThat(actual).isInstanceOf(ValidatorResult.Valid::class.java)
    }

    @Test
    fun `validate should return invalid when there is at least one validator error`() {
        val emptyTextValidator = mock<EmptyTextValidator>()
        val text = "text"
        whenever(emptyTextValidator.validate(text)).thenReturn(ValidatorResult.Valid)
        val emailFormatValidator = mock<EmailFormatValidator>()
        whenever(emailFormatValidator.validate(text)).thenReturn(ValidatorResult.Invalid)
        val testObject = CompositeValidator(emptyTextValidator, emailFormatValidator)

        val actual = testObject.validate(text)

        assertThat(actual).isInstanceOf(ValidatorResult.Invalid::class.java)
    }
}
