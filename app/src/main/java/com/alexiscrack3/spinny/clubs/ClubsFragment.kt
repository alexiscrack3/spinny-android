package com.alexiscrack3.spinny.clubs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.OrientationHelper
import com.alexiscrack3.spinny.R
import com.alexiscrack3.spinny.api.Result
import com.alexiscrack3.spinny.databinding.ClubsFragmentBinding
import com.alexiscrack3.spinny.models.Club
import kotlinx.android.synthetic.main.fragment_clubs.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class ClubsFragment : Fragment() {
    private val clubsViewModel by viewModel<ClubsViewModel>()
    private val clubsAdapter by inject<ClubsAdapter>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val observer = Observer<Result<List<Club>>> { result ->
            when (result) {
                is Result.Success -> {
                    clubsAdapter.swap(result.data.orEmpty())
                }
                is Result.Failure -> Timber.e(result.error)
            }
        }
        clubsViewModel.clubsLiveData.observe(this, observer)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<ClubsFragmentBinding>(
            inflater,
            R.layout.fragment_clubs,
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
        clubs_list.addItemDecoration(dividerItemDecoration)
        clubs_list.adapter = clubsAdapter
    }

    override fun onResume() {
        super.onResume()
        clubsViewModel.getClubs()
    }
}
