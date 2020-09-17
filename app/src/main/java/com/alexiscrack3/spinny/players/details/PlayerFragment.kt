package com.alexiscrack3.spinny.players.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.alexiscrack3.spinny.databinding.PlayerFragmentBinding
import org.koin.android.viewmodel.ext.android.viewModel

class PlayerFragment : Fragment() {
    private val playerViewModel by viewModel<PlayerViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setPlayerObserver()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = PlayerFragmentBinding.inflate(
            inflater,
            container,
            false
        ).apply {
            lifecycleOwner = this@PlayerFragment
            viewModel = playerViewModel
        }
        return binding.root
    }

    private fun setPlayerObserver() {
        playerViewModel.getPlayer()
    }
}
