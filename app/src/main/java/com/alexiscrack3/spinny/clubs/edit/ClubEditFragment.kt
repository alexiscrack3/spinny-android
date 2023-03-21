package com.alexiscrack3.spinny.clubs.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.alexiscrack3.spinny.R
import com.alexiscrack3.spinny.databinding.FragmentClubEditBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ClubEditFragment : Fragment() {
    private var _binding: FragmentClubEditBinding? = null
    private val clubEditViewModel by viewModels<ClubEditViewModel>()

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
        _binding = FragmentClubEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clubEditViewModel.clubState.observe(viewLifecycleOwner) {
            binding.idTextView.text = it?.id.toString()
            binding.nameTextView.text = it?.name
            binding.descriptionTextView.text = it?.description
        }
        clubEditViewModel.deleteState.observe(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
        binding.membersButton.setOnClickListener {
            val clubId = requireArguments().getInt(CLUB_ID_KEY)
            val args = bundleOf(CLUB_ID_KEY to clubId)
            it.findNavController().navigate(R.id.action_clubFragment_to_clubMembersFragment, args)
        }
        binding.deleteButton.setOnClickListener {
//            val clubId = Integer.parseInt(binding.idTextView.text.toString())
            val clubId = requireArguments().getInt(CLUB_ID_KEY)
            clubEditViewModel.deleteClubById(clubId)
        }
    }

    override fun onResume() {
        super.onResume()
        val clubId = requireArguments().getInt(CLUB_ID_KEY)
        clubEditViewModel.getClubById(clubId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
