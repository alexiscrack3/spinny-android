package com.alexiscrack3.spinny.clubs.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.OrientationHelper
import com.alexiscrack3.spinny.R
import com.alexiscrack3.spinny.SpinnyFragment
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_club.*
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class ClubFragment : SpinnyFragment() {
    private val clubViewModel by viewModel<ClubViewModel>()

    companion object {
        const val CLUB_ID_KEY = "CLUB_ID"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_club, container, false)
    }

    override fun onStart() {
        super.onStart()
        val clubId = requireArguments().getString(CLUB_ID_KEY).orEmpty()
        clubViewModel.getClubId(clubId)
            .subscribeOn(Schedulers.io())
            .subscribe({
                val players = it.data.members
                club_players_list.adapter =
                    ClubPlayersAdapter(players + players + players + players + players + players + players + players + players + players + players + players + players + players + players)
            }, {
                Timber.e(it)
            })

        val dividerItemDecoration = DividerItemDecoration(
            requireContext(), OrientationHelper.VERTICAL
        )
        club_players_list.addItemDecoration(dividerItemDecoration)
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
    }

    override fun onPause() {
        super.onPause()
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
    }
}
