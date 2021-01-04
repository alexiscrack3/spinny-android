package com.alexiscrack3.spinny.clubs.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.OrientationHelper
import com.alexiscrack3.spinny.R
import com.alexiscrack3.spinny.SpinnyFragment
import com.alexiscrack3.spinny.api.Resource
import com.alexiscrack3.spinny.models.Club
import kotlinx.android.synthetic.main.fragment_club.*
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class ClubFragment : SpinnyFragment() {
    private val clubViewModel by viewModel<ClubViewModel>()

    companion object {
        const val CLUB_ID_KEY = "CLUB_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val observer = Observer<Resource<Club>> { resource ->
            when (resource) {
                is Resource.Success -> {
                    val adapter = club_players_list.adapter as ClubPlayersAdapter
                    adapter.swap(resource.data?.members.orEmpty())
                }
                is Resource.Failure -> {
                    Timber.e(resource.error)
                }
            }
        }
        clubViewModel.clubLiveData.observe(this, observer)
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
        val dividerItemDecoration = DividerItemDecoration(
            requireContext(), OrientationHelper.VERTICAL
        )
        club_players_list.addItemDecoration(dividerItemDecoration)
        club_players_list.adapter = ClubPlayersAdapter()
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
    }

    override fun onResume() {
        super.onResume()
        val clubId = requireArguments().getString(CLUB_ID_KEY).orEmpty()
        clubViewModel.getClubId(clubId)
    }

    override fun onPause() {
        super.onPause()
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
    }
}
