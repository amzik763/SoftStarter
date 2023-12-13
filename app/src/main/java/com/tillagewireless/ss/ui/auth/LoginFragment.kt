package com.tillagewireless.ss.ui.auth

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import com.tillagewireless.R
import com.tillagewireless.ss.data.network.Resource
import com.tillagewireless.databinding.FragmentLoginBinding
import com.tillagewireless.ss.ui.enable
import com.tillagewireless.ss.ui.handleApiError
import com.tillagewireless.ss.ui.home.HomeActivity
import com.tillagewireless.ss.ui.startNewActivity
import com.tillagewireless.ss.ui.visible


@AndroidEntryPoint
class LoginFragment: Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel by activityViewModels<AuthViewModel>()

    private var navController : NavController? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        binding = FragmentLoginBinding.bind(view)
        binding.progressbar.visible(false)
        binding.btLogin.enable(false)

        viewModel.loginResponse.observe(viewLifecycleOwner, Observer {
            binding.progressbar.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    lifecycleScope.launch {
                        viewModel.saveAccessTokens(
                            it.value.access_token!!,
                            it.value.refresh_token!!
                        )
                    }
                    viewModel.clearLoginResponse()
                    requireActivity().startNewActivity(HomeActivity::class.java)
                }
                is Resource.Failure -> handleApiError(it)
                else -> {}
            }
        })

        binding.etPassword.addTextChangedListener {
            val mobileNumber = binding.etPhone.text.toString().trim()
            if (it != null) {
                if(it.length < 8){
                    binding.btLogin.enable(false)
                }else{
                    binding.btLogin.enable(mobileNumber.isNotEmpty())
                }
            }
        }

        binding.etPhone.addTextChangedListener{
            val password = binding.etPassword.text.toString().trim()
            if (it != null) {
                if(it.length < 10){
                    binding.btLogin.enable(false)
                }else{
                    binding.btLogin.enable(password.isNotEmpty())
                }
            }
        }

        binding.btLogin.setOnClickListener {
            login()
        }
        binding.textViewRegisterNow.setOnClickListener{
            viewModel.clearLoginResponse()
            navController!!.navigate(R.id.action_loginFragment_to_registrationFragment)
        }

        binding.textViewForgotPassword.setOnClickListener{
            viewModel.clearLoginResponse()
            navController!!.navigate(R.id.action_loginFragment_to_forgetPasswordFragment)
        }
    }

    private fun login() {
        val mobileNumber = "+91${binding.etPhone.text.toString().trim()}"
        val password = binding.etPassword.text.toString().trim()
        viewModel.login(mobileNumber, password)
    }
}