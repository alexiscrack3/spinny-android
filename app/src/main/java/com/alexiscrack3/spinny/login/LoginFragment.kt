package com.alexiscrack3.spinny.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.alexiscrack3.spinny.R
import com.alexiscrack3.spinny.api.ApiResponse
import com.alexiscrack3.spinny.api.LoginRequest
import com.alexiscrack3.spinny.api.LoginResponse
import com.alexiscrack3.spinny.api.LoginService
import com.alexiscrack3.spinny.databinding.FragmentLoginBinding
import com.alexiscrack3.spinny.helpers.RetrofitHelper
import com.alexiscrack3.spinny.models.PlayerRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginButton.setOnClickListener {
            val playerRequest = PlayerRequest(
                email = binding.emailEditText.text.toString(),
                password = binding.passwordEditText.text.toString()
            )
            val loginService = RetrofitHelper.getInstance().create(LoginService::class.java)
            loginService.signIn(LoginRequest(playerRequest))?.enqueue(object : Callback<ApiResponse<LoginResponse>?> {
                override fun onResponse(
                    call: Call<ApiResponse<LoginResponse>?>,
                    apiResponse: Response<ApiResponse<LoginResponse>?>
                ) {
                    if (apiResponse.isSuccessful) {
                        val result = apiResponse.body()
                        findNavController().navigate(R.id.action_LoginFragment_to_clubsFragment)
                    }
                }

                override fun onFailure(call: Call<ApiResponse<LoginResponse>?>, t: Throwable) {
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
