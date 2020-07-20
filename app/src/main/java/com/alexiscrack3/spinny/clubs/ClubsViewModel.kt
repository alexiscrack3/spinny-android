package com.alexiscrack3.spinny.clubs

import com.alexiscrack3.spinny.SpinnyViewModel
import com.alexiscrack3.spinny.models.Club
import io.reactivex.Single

class ClubsViewModel(
    private val clubsRepository: ClubsRepository
) : SpinnyViewModel() {

    fun getClubs(): Single<List<Club>> {
        return Single.never()
    }
}
