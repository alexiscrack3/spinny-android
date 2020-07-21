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
import com.alexiscrack3.spinny.api.Resource
import com.alexiscrack3.spinny.databinding.LoginFragmentBinding
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class LoginFragment : Fragment() {
    private val loginViewModel by viewModel<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val observer = Observer<Resource<String>> { resource ->
            when (resource) {
                is Resource.Success -> {
                    findNavController().navigate(R.id.action_loginFragment_to_main_nav_graph)
                }
                is Resource.Failure -> Timber.e(resource.error)
            }
        }
        loginViewModel.tokenLiveData.observe(this, observer)
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
