package com.alexiscrack3.spinny.api

import com.alexiscrack3.spinny.BuildConfig
import com.google.gson.Gson
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.KoinComponent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit

class ServicesFactory(
    baseUrl: String = BuildConfig.BASE_URL
) : KoinComponent {
    private var retrofit: Retrofit

    init {
        val okHttpClient = buildOkHttpClient()
        retrofit = buildRetrofit(baseUrl, okHttpClient, Gson())
    }

    private fun buildOkHttpClient(): OkHttpClient {
        val logger = object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Timber.tag("OkHttp").d(message)
            }
        }
        val httpLoggingInterceptor = HttpLoggingInterceptor(logger)
        return OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    private fun buildRetrofit(baseUrl: String, httpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(
                RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())
            )
            .build()
    }

    fun <S> createService(service: Class<S>): S = retrofit.create(service)
}
