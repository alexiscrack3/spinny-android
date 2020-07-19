package com.alexiscrack3.spinny.validators

import androidx.core.util.PatternsCompat

class EmailFormatValidator(
    private val errorString: String? = null
) : TextValidator {

    override fun validate(text: String): ValidatorResult {
        return if (PatternsCompat.EMAIL_ADDRESS.matcher(text).matches())
            ValidatorResult.Valid
        else
            ValidatorResult.Invalid.apply { error = ValidatorError(errorString) }
    }
}
