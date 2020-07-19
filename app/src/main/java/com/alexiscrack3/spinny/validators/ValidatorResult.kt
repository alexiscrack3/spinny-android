package com.alexiscrack3.spinny.validators

sealed class ValidatorResult {
    object Valid : ValidatorResult()
    object Invalid : ValidatorResult() {
        var error: ValidatorError? = null
    }
}
