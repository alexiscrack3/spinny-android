package com.alexiscrack3.spinny.di

import com.alexiscrack3.spinny.api.AuthHeaderInterceptor
import com.alexiscrack3.spinny.helpers.TokenStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.logging.HttpLoggingInterceptor

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    fun providesAuthLoggingInterceptor(tokenStore: TokenStore) = AuthHeaderInterceptor(tokenStore)
}
