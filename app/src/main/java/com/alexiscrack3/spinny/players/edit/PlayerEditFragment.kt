package com.alexiscrack3.spinny.players.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.alexiscrack3.spinny.databinding.FragmentPlayerEditBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlayerEditFragment : Fragment() {
    private var _binding: FragmentPlayerEditBinding? = null
    private val playerEditViewModel by viewModels<PlayerEditViewModel>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    companion object {
        const val PLAYER_ID_KEY = "PLAYER_ID"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlayerEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        playerEditViewModel.playerState.observe(viewLifecycleOwner) {
            binding.idTextView.text = it?.id.toString()
            binding.firstNameTextView.text = it?.firstName
            binding.lastNameTextView.text = it?.lastName
            binding.emailTextView.text = it?.email
        }
    }

    override fun onResume() {
        super.onResume()
        val clubId = requireArguments().getInt(PLAYER_ID_KEY)
        playerEditViewModel.getPlayerById(clubId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
