package com.alexiscrack3.spinny.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.alexiscrack3.spinny.R
import com.alexiscrack3.spinny.SpinnyFragment
import com.alexiscrack3.spinny.databinding.SignUpFragmentBinding
import org.koin.android.viewmodel.ext.android.viewModel

class EnrollmentFragment : SpinnyFragment() {
    private val enrollmentViewModel by viewModel<EnrollmentViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<SignUpFragmentBinding>(
            inflater,
            R.layout.fragment_enrollment,
            container,
            false
        ).apply {
            lifecycleOwner = this@EnrollmentFragment
            viewModel = enrollmentViewModel
        }
        return binding.root
    }
}
