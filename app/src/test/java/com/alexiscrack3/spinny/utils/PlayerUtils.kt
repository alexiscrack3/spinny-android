package com.alexiscrack3.spinny.utils

import com.alexiscrack3.spinny.models.Player
import org.apache.commons.lang3.RandomStringUtils
import kotlin.random.Random

fun Player.Companion.test(
    id: String = RandomStringUtils.randomAlphanumeric(10),
    email: String = RandomStringUtils.randomAlphanumeric(10),
    firstName: String = RandomStringUtils.randomAlphanumeric(10),
    lastName: String = RandomStringUtils.randomAlphanumeric(10),
    rating: Int = Random.nextInt(10)
): Player {
    return Player(
        id = id,
        email = email,
        firstName = firstName,
        lastName = lastName,
        rating = rating
    )
}
