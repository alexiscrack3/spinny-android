package com.alexiscrack3.spinny.clubs

import com.alexiscrack3.spinny.api.ServicesFactory
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val clubsModule = module {
    factory {
        val serviceFactory = get<ServicesFactory>()
        serviceFactory.createService(ClubsService::class.java)
    }
    factory {
        ClubsRepository(
            clubsService = get(),
            clubsDao = get(),
            transactionLogsDao = get()
        )
    }
    viewModel {
        ClubsViewModel(clubsRepository = get())
    }
}
