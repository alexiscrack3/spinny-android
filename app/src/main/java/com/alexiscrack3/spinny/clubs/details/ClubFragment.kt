package com.alexiscrack3.spinny.clubs.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.OrientationHelper
import com.alexiscrack3.spinny.SpinnyFragment
import com.alexiscrack3.spinny.api.Resource
import com.alexiscrack3.spinny.databinding.ClubFragmentBinding
import com.alexiscrack3.spinny.models.Club
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class ClubFragment : SpinnyFragment() {
    private lateinit var binding: ClubFragmentBinding
    private val clubViewModel by viewModel<ClubViewModel>()
    private val clubPlayersAdapter by inject<ClubPlayersAdapter>()
    private val navController by lazy { this.findNavController() }
    private val appBarConfiguration by lazy { AppBarConfiguration(navController.graph) }

    companion object {
        const val CLUB_ID_KEY = "CLUB_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val observer = Observer<Resource<Club>> { resource ->
            when (resource) {
                is Resource.Success -> {
                    clubPlayersAdapter.swap(resource.data?.members.orEmpty())
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
        binding = ClubFragmentBinding.inflate(
            inflater,
            container,
            false
        ).apply {
            lifecycleOwner = this@ClubFragment
            viewModel = clubViewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.clubCollapsingToolbarLayout.setupWithNavController(
            binding.clubToolbar,
            navController,
            appBarConfiguration
        )
//        binding.clubToolbar.inflateMenu(R.menu.main)
//        binding.clubToolbar.setOnMenuItemClickListener { item ->
//            val navController = findNavController()
//            item?.onNavDestinationSelected(navController) ?: false
//        }

        val supportActionBar = (requireActivity() as? AppCompatActivity)?.supportActionBar
        supportActionBar?.hide()
        supportActionBar?.setShowHideAnimationEnabled(false)
    }

    override fun onStart() {
        super.onStart()
        val dividerItemDecoration = DividerItemDecoration(
            requireContext(), OrientationHelper.VERTICAL
        )
        binding.clubPlayersList.addItemDecoration(dividerItemDecoration)
        binding.clubPlayersList.adapter = clubPlayersAdapter
    }

    override fun onResume() {
        super.onResume()
        val clubId = requireArguments().getString(CLUB_ID_KEY).orEmpty()
        clubViewModel.getClubId(clubId)
    }

    override fun onStop() {
        super.onStop()
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
    }
}
