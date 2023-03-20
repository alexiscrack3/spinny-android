package com.alexiscrack3.spinny.clubs.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.alexiscrack3.spinny.databinding.FragmentClubBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ClubFragment : Fragment() {
    private var _binding: FragmentClubBinding? = null
    private val clubViewModel by viewModels<ClubViewModel>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    companion object {
        const val CLUB_ID_KEY = "CLUB_ID"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentClubBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clubViewModel.clubState.observe(viewLifecycleOwner) {
            binding.nameTextView.text = it?.name
        }
    }

    override fun onResume() {
        super.onResume()
        val clubId = requireArguments().getInt(CLUB_ID_KEY)
        clubViewModel.getClubById(clubId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
