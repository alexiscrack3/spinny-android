package com.alexiscrack3.spinny.clubs.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.alexiscrack3.spinny.databinding.FragmentNewClubBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateClubFragment : Fragment() {
    private var _binding: FragmentNewClubBinding? = null
    private val createClubViewModel by viewModels<CreateClubViewModel>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewClubBinding.inflate(inflater, container, false)
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
                createClubViewModel.createClub(name, description)
            }
        }
        createClubViewModel.clubState.observe(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}