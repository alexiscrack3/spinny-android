package com.alexiscrack3.spinny.clubs

import com.alexiscrack3.spinny.NetworkModule
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val clubsModule = module {
    factory { NetworkModule.createService(ClubsService::class.java) }
    factory { ClubsRepository(clubsService = get()) }
    viewModel {
        ClubsViewModel(clubsRepository = get())
    }
}
