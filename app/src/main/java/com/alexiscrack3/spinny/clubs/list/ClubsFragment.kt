package com.alexiscrack3.spinny.clubs.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.OrientationHelper
import com.alexiscrack3.spinny.SpinnyFragment
import com.alexiscrack3.spinny.api.Resource
import com.alexiscrack3.spinny.databinding.ClubsFragmentBinding
import com.alexiscrack3.spinny.models.Club
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class ClubsFragment : SpinnyFragment() {
    private lateinit var binding: ClubsFragmentBinding
    private val clubsViewModel by viewModel<ClubsViewModel>()
    private val clubsAdapter by inject<ClubsAdapter>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val observer = Observer<Resource<List<Club>>> { resource ->
            when (resource) {
                is Resource.Success -> {
                    clubsAdapter.swap(resource.data.orEmpty())
                    binding.clubsSwipeRefreshLayout.isRefreshing = false
                }
                is Resource.Failure -> {
                    Timber.e(resource.error)
                    binding.clubsSwipeRefreshLayout.isRefreshing = false
                }
            }
        }
        clubsViewModel.clubsLiveData.observe(this, observer)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ClubsFragmentBinding.inflate(
            inflater,
            container,
            false
        ).apply {
            lifecycleOwner = this@ClubsFragment
            viewModel = clubsViewModel
        }
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val dividerItemDecoration = DividerItemDecoration(
            requireContext(), OrientationHelper.VERTICAL
        )
        binding.clubsSwipeRefreshLayout.setOnRefreshListener {
            clubsViewModel.getClubs()
        }
        binding.clubsList.addItemDecoration(dividerItemDecoration)
        binding.clubsList.adapter = clubsAdapter
    }

    override fun onResume() {
        super.onResume()
        clubsViewModel.getClubs()
    }
}
