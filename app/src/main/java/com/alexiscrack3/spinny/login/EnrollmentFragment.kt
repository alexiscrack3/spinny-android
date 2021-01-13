package com.alexiscrack3.spinny.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.alexiscrack3.spinny.MainActivity
import com.alexiscrack3.spinny.R
import com.alexiscrack3.spinny.SpinnyFragment
import com.alexiscrack3.spinny.api.Resource
import com.alexiscrack3.spinny.databinding.EnrollmentFragmentBinding
import com.alexiscrack3.spinny.validators.ValidatorResult
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.fragment_enrollment.*
import org.koin.android.viewmodel.ext.android.viewModel

class EnrollmentFragment : SpinnyFragment() {
    private val enrollmentViewModel by viewModel<EnrollmentViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val enrollmentObserver = Observer<Resource<String>> { resource ->
            when (resource) {
                is Resource.Success -> {
                    val intent = MainActivity.getIntent(requireContext())
                    startActivity(intent)
                }
                is Resource.Failure -> showLoginError()
            }
        }
        enrollmentViewModel.enrollmentLiveData.observe(this, enrollmentObserver)

        val emailObserver = Observer<ValidatorResult> { result ->
            enrollment_email_layout.error = if (result == ValidatorResult.Success) {
                null
            } else {
                requireContext().getString(R.string.email_error)
            }
        }
        enrollmentViewModel.emailErrorLiveData.observe(this, emailObserver)

        val passwordObserver = Observer<ValidatorResult> { result ->
            enrollment_password_layout.error = if (result == ValidatorResult.Success) {
                null
            } else {
                requireContext().getString(R.string.password_error)
            }
        }
        enrollmentViewModel.passwordErrorLiveData.observe(this, passwordObserver)
    }

    private fun showLoginError() {
        MaterialAlertDialogBuilder(requireContext(), R.style.Theme_Spinny)
            .setTitle(R.string.enrollment_error_title)
            .setMessage(R.string.enrollment_error_message)
            .setPositiveButton(android.R.string.ok, null)
            .show()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<EnrollmentFragmentBinding>(
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
