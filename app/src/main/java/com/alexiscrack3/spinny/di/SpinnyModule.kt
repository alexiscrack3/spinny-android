package com.alexiscrack3.spinny.di

import android.content.Context
import com.alexiscrack3.spinny.helpers.TokenStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SpinnyModule {
    @Provides
    @Singleton
    fun providesTokenStore(@ApplicationContext appContext: Context) = TokenStore(appContext)
}
