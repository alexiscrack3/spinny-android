package com.alexiscrack3.spinny.clubs.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alexiscrack3.spinny.SpinnyFragment
import com.alexiscrack3.spinny.databinding.CreateClubFragmentBinding
import org.koin.android.viewmodel.ext.android.viewModel

class CreateClubFragment : SpinnyFragment() {
    private val createClubViewModel by viewModel<CreateClubViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = CreateClubFragmentBinding.inflate(
            inflater,
            container,
            false
        ).apply {
            lifecycleOwner = this@CreateClubFragment
            viewModel = createClubViewModel
        }
        return binding.root
    }
}
