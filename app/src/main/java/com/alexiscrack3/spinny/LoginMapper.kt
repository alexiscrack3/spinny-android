package com.alexiscrack3.spinny

import com.alexiscrack3.spinny.api.models.LoginData
import com.alexiscrack3.spinny.models.Player

class LoginMapper {
    fun map(loginResponse: LoginData): Player {
        return Player(
            id = loginResponse.id,
            firstName = loginResponse.firstName,
            lastName = loginResponse.lastName,
            email = loginResponse.email
        )
    }
}
