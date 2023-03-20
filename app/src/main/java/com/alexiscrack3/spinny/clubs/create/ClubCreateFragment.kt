package com.alexiscrack3.spinny.clubs.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.alexiscrack3.spinny.databinding.FragmentClubCreateBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ClubCreateFragment : Fragment() {
    private var _binding: FragmentClubCreateBinding? = null
    private val clubCreateViewModel by viewModels<ClubCreateViewModel>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentClubCreateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.createButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val description = binding.descriptionEditText.text.toString()
            if (name.isBlank()) {
                Snackbar
                    .make(binding.root, "Name can't be empty", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            } else {
                clubCreateViewModel.createClub(name, description)
            }
        }
        clubCreateViewModel.clubState.observe(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}