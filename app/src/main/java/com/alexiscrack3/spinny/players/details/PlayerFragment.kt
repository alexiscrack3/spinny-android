package com.alexiscrack3.spinny.players.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.alexiscrack3.spinny.databinding.PlayerFragmentBinding
import com.google.android.material.snackbar.Snackbar
import org.koin.android.viewmodel.ext.android.viewModel

class PlayerFragment : Fragment() {
    private lateinit var binding: PlayerFragmentBinding
    private val playerViewModel by viewModel<PlayerViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setProfileErrorObserver()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PlayerFragmentBinding.inflate(
            inflater,
            container,
            false
        ).apply {
            lifecycleOwner = this@PlayerFragment
            viewModel = playerViewModel
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        playerViewModel.getPlayer()
    }

    private fun setProfileErrorObserver() {
        val profileErrorObserver = Observer<String> { errorMessage ->
            Snackbar
                .make(binding.root, errorMessage, Snackbar.LENGTH_SHORT)
                .show()
        }
        playerViewModel.profileErrorState.observe(this, profileErrorObserver)
    }
}
