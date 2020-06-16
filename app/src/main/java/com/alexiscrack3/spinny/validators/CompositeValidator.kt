package com.alexiscrack3.spinny.validators

class CompositeValidator(private vararg var validators: TextValidator) : TextValidator {

    override fun validate(text: String): ValidatorResult {
        for (validator in validators) {
            val result = validator.validate(text)
            if (result == ValidatorResult.INVALID) {
                return result
            }
        }
        return ValidatorResult.VALID
    }
}
