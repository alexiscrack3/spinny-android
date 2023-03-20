package com.alexiscrack3.spinny.di

import com.alexiscrack3.spinny.api.ClubsService
import com.alexiscrack3.spinny.clubs.ClubsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ClubsModule {

    @Provides
    fun getClubsRepository(clubsService: ClubsService): ClubsRepository {
        return ClubsRepository(clubsService)
    }
}
