package com.alexiscrack3.spinny.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.alexiscrack3.spinny.R
import com.alexiscrack3.spinny.api.Result
import com.alexiscrack3.spinny.databinding.LoginFragmentBinding
import com.alexiscrack3.spinny.validators.ValidatorResult
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.android.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {
    private val loginViewModel by viewModel<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tokenObserver = Observer<Result<String>> { result ->
            when (result) {
                is Result.Success -> {
                    findNavController().navigate(R.id.action_loginFragment_to_main_nav_graph)
                }
                is Result.Failure -> Timber.e(result.error)
            }
        }
        loginViewModel.tokenLiveData.observe(this, tokenObserver)

        val emailObserver = Observer<ValidatorResult> { result ->
            login_email_layout.error = if (result == ValidatorResult.Valid) {
                null
            } else {
                context?.getString(R.string.email_error)
            }
        }
        loginViewModel.emailError.observe(this, emailObserver)

        val passwordObserver = Observer<ValidatorResult> { result ->
            login_password_layout.error = if (result == ValidatorResult.Valid) {
                null
            } else {
                context?.getString(R.string.password_error)
            }
        }
        loginViewModel.passwordError.observe(this, passwordObserver)
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
