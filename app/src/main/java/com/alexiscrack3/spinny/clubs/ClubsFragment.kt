package com.alexiscrack3.spinny.clubs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.alexiscrack3.spinny.R
import com.alexiscrack3.spinny.databinding.ClubsFragmentBinding
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class ClubsFragment : Fragment() {
    private val clubsViewModel by viewModel<ClubsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        clubsViewModel.getClubs()
            .subscribe({
                Timber.d(it.toString())
            }, {
                Timber.e(it)
            })
//        val observer = Observer<Resource<String>> { resource ->
//            when (resource) {
//                is Resource.Success -> {
//                    val accessToken = resource.data.orEmpty()
//                    securePreferences.setAccessToken(accessToken)
//                }
//                is Resource.Failure -> Timber.e(resource.error)
//            }
//        }
//        loginViewModel.tokenLiveData.observe(this, observer)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<ClubsFragmentBinding>(
            inflater,
            R.layout.fragment_clubs,
            container,
            false
        ).apply {
//            lifecycleOwner = this@LoginFragment
            viewModel = clubsViewModel
        }
        return binding.root
    }
}
