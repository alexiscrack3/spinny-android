package com.alexiscrack3.spinny.clubs

import com.alexiscrack3.spinny.api.ServicesFactory
import com.alexiscrack3.spinny.clubs.details.ClubViewModel
import com.alexiscrack3.spinny.clubs.list.ClubsAdapter
import com.alexiscrack3.spinny.clubs.list.ClubsViewModel
import com.alexiscrack3.spinny.clubs.create.CreateClubViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val clubsModule = module {
    factory {
        val serviceFactory = get<ServicesFactory>()
        serviceFactory.createService(ClubsService::class.java)
    }
    factory {
        ClubsAdapter()
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
    viewModel {
        ClubViewModel(clubsRepository = get())
    }
    viewModel {
        CreateClubViewModel(clubsRepository = get())
    }
}
