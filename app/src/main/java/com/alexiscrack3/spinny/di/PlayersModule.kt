package com.alexiscrack3.spinny.di

import com.alexiscrack3.spinny.api.PlayersService
import com.alexiscrack3.spinny.players.PlayersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object PlayersModule {

    @Provides
    fun providesPlayersRepository(playersService: PlayersService): PlayersRepository {
        return PlayersRepository(playersService)
    }
}
