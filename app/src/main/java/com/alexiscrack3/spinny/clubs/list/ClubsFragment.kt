package com.alexiscrack3.spinny.clubs.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.alexiscrack3.spinny.R
import com.alexiscrack3.spinny.databinding.FragmentClubsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ClubsFragment : Fragment() {
    private var _binding: FragmentClubsBinding? = null
    private val clubsViewModel by viewModels<ClubsViewModel>()
    private var columnCount = 1

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentClubsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.clubsList.adapter = ClubsAdapter()
        binding.createFab.setOnClickListener {
            it.findNavController().navigate(R.id.action_clubsFragment_to_createClubFragment)
        }
        clubsViewModel.clubsState.observe(viewLifecycleOwner) {
            val adapter = binding.clubsList.adapter as ClubsAdapter
            adapter.swap(it)
        }
    }

    override fun onResume() {
        super.onResume()
        clubsViewModel.getClubs()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            ClubsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}