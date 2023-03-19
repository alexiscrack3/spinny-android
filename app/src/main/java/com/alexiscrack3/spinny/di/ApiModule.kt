package com.alexiscrack3.spinny.di

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
    fun provideLoginService(retrofit: Retrofit) = retrofit.create(LoginService::class.java)
}
