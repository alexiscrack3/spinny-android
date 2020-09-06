package com.alexiscrack3.spinny.validators

interface Validator {
    fun validate(text: String): ValidatorResult
}
