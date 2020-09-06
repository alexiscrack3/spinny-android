package com.alexiscrack3.spinny.validators

class EmptyTextValidator : Validator {

    override fun validate(text: String): ValidatorResult {
        return if (text.isBlank()) {
            ValidatorResult.Failure
        } else {
            ValidatorResult.Success
        }
    }
}
