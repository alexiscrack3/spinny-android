package com.alexiscrack3.spinny.utils

import com.alexiscrack3.spinny.api.ClubResponse
import com.alexiscrack3.spinny.api.ClubsResponse
import com.alexiscrack3.spinny.models.Club
import com.alexiscrack3.spinny.models.Player
import org.apache.commons.lang3.RandomStringUtils
import java.util.*
import kotlin.random.Random

fun Club.Companion.test(
    id: String = RandomStringUtils.randomAlphanumeric(10),
    name: String = RandomStringUtils.randomAlphanumeric(10),
    imageUrl: String = RandomStringUtils.randomAlphanumeric(10),
    membersCount: Int = Random.nextInt(10)
) = Club(
    id = id,
    name = name,
    imageUrl = imageUrl,
    membersCount = membersCount
)

fun ClubResponse.Companion.test(
    id: String = RandomStringUtils.randomAlphanumeric(10),
    name: String = RandomStringUtils.randomAlphanumeric(10),
    createdAt: Date = Date(),
    imageUrl: String = RandomStringUtils.randomAlphanumeric(10),
    members: List<Player> = emptyList(),
    membersCount: Int = 0
) = ClubResponse(
    id = id,
    name = name,
    createdAt = createdAt,
    imageUrl = imageUrl,
    members = members,
    membersCount = membersCount
)

fun ClubsResponse.Companion.test(
    id: String = RandomStringUtils.randomAlphanumeric(10),
    name: String = RandomStringUtils.randomAlphanumeric(10),
    createdAt: Date = Date(),
    imageUrl: String = RandomStringUtils.randomAlphanumeric(10),
    membersCount: Int = 0
) = ClubsResponse(
    id = id,
    name = name,
    createdAt = createdAt,
    imageUrl = imageUrl,
    membersCount = membersCount
)
