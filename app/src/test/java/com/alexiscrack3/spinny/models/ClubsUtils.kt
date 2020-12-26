package com.alexiscrack3.spinny.models

import org.apache.commons.lang3.RandomStringUtils
import kotlin.random.Random

fun Club.Companion.test(
    id: String = RandomStringUtils.randomAlphanumeric(10),
    name: String = RandomStringUtils.randomAlphanumeric(10),
    imageUrl: String = RandomStringUtils.randomAlphanumeric(10),
    membersCount: Int = Random.nextInt(10)
): Club {
    return Club(
        id = id,
        name = name,
        imageUrl = imageUrl,
        membersCount = membersCount
    )
}
