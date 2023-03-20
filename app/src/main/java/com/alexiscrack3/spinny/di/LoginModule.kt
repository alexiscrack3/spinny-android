package com.alexiscrack3.spinny.di

import com.alexiscrack3.spinny.api.LoginService
import com.alexiscrack3.spinny.login.LoginRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object LoginModule {

    @Provides
    fun getLoginRepository(loginService: LoginService): LoginRepository {
        return LoginRepository(loginService)
    }
}
