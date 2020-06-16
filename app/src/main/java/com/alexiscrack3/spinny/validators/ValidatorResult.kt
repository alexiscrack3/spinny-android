package com.alexiscrack3.spinny.validators

sealed class ValidatorResult {
    object VALID : ValidatorResult()
    object INVALID : ValidatorResult() {
        var error: ValidatorError? = null
    }
}
