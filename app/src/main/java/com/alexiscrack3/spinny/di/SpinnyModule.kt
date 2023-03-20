package com.alexiscrack3.spinny.di

import android.content.Context
import com.alexiscrack3.spinny.api.AuthHeaderInterceptor
import com.alexiscrack3.spinny.api.LoginService
import com.alexiscrack3.spinny.helpers.TokenStore
import com.alexiscrack3.spinny.login.LoginRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SpinnyModule {
    private const val BASE_URL = "http://10.0.2.2:3000"

    @Provides
    @Singleton
    fun providesOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        authHeaderInterceptor: AuthHeaderInterceptor
    ): OkHttpClient {
        return OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(authHeaderInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun providesTokenStore(@ApplicationContext appContext: Context) = TokenStore(appContext)
}
