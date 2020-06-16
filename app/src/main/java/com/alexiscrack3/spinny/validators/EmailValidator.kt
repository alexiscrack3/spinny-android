package com.alexiscrack3.spinny.validators

import android.util.Patterns

class EmailFormatValidator(
    private val errorString: String
) : TextValidator {

    override fun validate(text: String): ValidatorResult {
        return if (Patterns.EMAIL_ADDRESS.matcher(text).matches())
            ValidatorResult.VALID
        else
            ValidatorResult.INVALID.apply { error = ValidatorError(errorString) }
    }
}
