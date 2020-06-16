package com.alexiscrack3.spinny.validators

class EmptyTextValidator(
    private val errorString: String
) : TextValidator {

    override fun validate(text: String): ValidatorResult {
        return if (text.isBlank()) {
            ValidatorResult.INVALID.apply { error = ValidatorError(errorString) }
        } else {
            ValidatorResult.VALID
        }
    }
}
