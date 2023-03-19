package com.alexiscrack3.spinny

import com.alexiscrack3.spinny.api.LoginResponse
import com.alexiscrack3.spinny.models.Player

class PlayersMapper {
    fun map(loginResponse: LoginResponse): Player {
        return Player(
            id = loginResponse.id,
            firstName = loginResponse.firstName,
            lastName = loginResponse.lastName,
            email = loginResponse.email
        )
    }
}
