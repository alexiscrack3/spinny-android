package com.alexiscrack3.spinny.validators

class CompositeValidator(
    private vararg var validators: Validator
) : Validator {

    override fun validate(text: String): ValidatorResult {
        for (validator in validators) {
            val result = validator.validate(text)
            if (result == ValidatorResult.Failure) {
                return result
            }
        }
        return ValidatorResult.Success
    }
}
