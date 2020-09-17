package com.alexiscrack3.spinny.players

import com.alexiscrack3.spinny.api.ServicesFactory
import com.alexiscrack3.spinny.players.details.PlayerViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val playersModule = module {
    factory {
        val serviceFactory = get<ServicesFactory>()
        serviceFactory.createService(PlayersService::class.java)
    }
    factory {
        PlayersRepository(
            playersService = get()
        )
    }
    viewModel {
        PlayerViewModel(
            playersRepository = get()
        )
    }
}
