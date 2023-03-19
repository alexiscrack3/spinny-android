package com.alexiscrack3.spinny.di

import com.alexiscrack3.spinny.api.ClubsService
import com.alexiscrack3.spinny.api.LoginService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun providesLoginService(retrofit: Retrofit) = retrofit.create(LoginService::class.java)

    @Provides
    @Singleton
    fun providesClubsService(retrofit: Retrofit) = retrofit.create(ClubsService::class.java)
}
