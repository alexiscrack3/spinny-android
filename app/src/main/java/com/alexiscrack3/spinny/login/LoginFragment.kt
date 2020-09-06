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
import com.alexiscrack3.spinny.databinding.LoginFragmentBinding
import com.alexiscrack3.spinny.validators.ValidatorResult
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.android.viewmodel.ext.android.viewModel

class LoginFragment : SpinnyFragment() {
    private val loginViewModel by viewModel<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setAuthenticationObserver()
        setEmailErrorObserver()
        setPasswordErrorObserver()
    }

    private fun setPasswordErrorObserver() {
        val passwordErrorObserver = Observer<ValidatorResult> { result ->
            login_password_layout.error = if (result == ValidatorResult.Success) {
                null
            } else {
                requireContext().getString(R.string.password_error)
            }
        }
        loginViewModel.passwordErrorState.observe(this, passwordErrorObserver)
    }

    private fun setEmailErrorObserver() {
        val emailErrorObserver = Observer<ValidatorResult> { result ->
            login_email_layout.error = if (result == ValidatorResult.Success) {
                null
            } else {
                requireContext().getString(R.string.email_error)
            }
        }
        loginViewModel.emailErrorState.observe(this, emailErrorObserver)
    }

    private fun setAuthenticationObserver() {
        val authenticationObserver = Observer<Resource<String>> { resource ->
            when (resource) {
                is Resource.Success -> {
                    val intent = MainActivity.getIntent(requireContext())
                    startActivity(intent)
                }
                is Resource.Failure -> showLoginError()
            }
        }
        loginViewModel.authenticationState.observe(this, authenticationObserver)
    }

    private fun showLoginError() {
        MaterialAlertDialogBuilder(requireContext(), R.style.Theme_PingPongComponents)
            .setTitle(R.string.login_error_title)
            .setMessage(R.string.login_error_message)
            .setPositiveButton(android.R.string.ok, null)
            .show()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<LoginFragmentBinding>(
            inflater,
            R.layout.fragment_login,
            container,
            false
        ).apply {
            lifecycleOwner = this@LoginFragment
            viewModel = loginViewModel
        }
        return binding.root
    }
}
