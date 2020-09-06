package com.alexiscrack3.spinny.validators

import androidx.core.util.PatternsCompat

class EmailFormatValidator : Validator {

    override fun validate(text: String): ValidatorResult {
        return if (PatternsCompat.EMAIL_ADDRESS.matcher(text).matches()) {
            ValidatorResult.Success
        } else {
            ValidatorResult.Failure
        }
    }
}
