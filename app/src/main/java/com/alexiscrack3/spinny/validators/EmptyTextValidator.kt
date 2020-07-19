package com.alexiscrack3.spinny.validators

class EmptyTextValidator(
    private val errorString: String? = null
) : TextValidator {

    override fun validate(text: String): ValidatorResult {
        return if (text.isBlank()) {
            ValidatorResult.Invalid.apply { error = ValidatorError(errorString) }
        } else {
            ValidatorResult.Valid
        }
    }
}
