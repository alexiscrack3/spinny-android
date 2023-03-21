package com.alexiscrack3.spinny.clubs.members

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.alexiscrack3.spinny.databinding.FragmentClubMembersBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ClubMembersFragment : Fragment() {
    private var _binding: FragmentClubMembersBinding? = null
    private val clubMembersViewModel by viewModels<ClubMembersViewModel>()

    private val binding get() = _binding!!

    companion object {
        const val CLUB_ID_KEY = "CLUB_ID"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentClubMembersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.clubMembersList.adapter = ClubMembersAdapter()
        clubMembersViewModel.membersState.observe(viewLifecycleOwner) {
            val adapter = binding.clubMembersList.adapter as ClubMembersAdapter
            adapter.swap(it)
        }
    }

    override fun onResume() {
        super.onResume()
        val clubId = requireArguments().getInt(CLUB_ID_KEY)
        clubMembersViewModel.getMembersByClubId(clubId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
