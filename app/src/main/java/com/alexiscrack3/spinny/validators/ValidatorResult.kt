package com.alexiscrack3.spinny.validators

sealed class ValidatorResult {
    object Success : ValidatorResult()
    object Failure : ValidatorResult()
}
