package com.alexiscrack3.spinny.clubs

import android.content.Context
import com.alexiscrack3.spinny.api.ServicesFactory
import com.alexiscrack3.spinny.clubs.create.CreateClubViewModel
import com.alexiscrack3.spinny.clubs.details.ClubPlayersAdapter
import com.alexiscrack3.spinny.clubs.details.ClubViewModel
import com.alexiscrack3.spinny.clubs.list.ClubsAdapter
import com.alexiscrack3.spinny.clubs.list.ClubsViewModel
import com.bumptech.glide.Glide
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
        ClubPlayersAdapter()
    }
    factory {
        ClubsRepository(
            clubsService = get(),
            clubsDao = get(),
            transactionLogsDao = get()
        )
    }
    factory { (context: Context) ->
        Glide.with(context)
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
