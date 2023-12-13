package com.tillagewireless.ss.ui.auth

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.tillagewireless.R
import com.tillagewireless.databinding.FragmentSetPasswordBinding
import com.tillagewireless.ss.data.network.Resource
import com.tillagewireless.ss.ui.enable
import com.tillagewireless.ss.ui.handleApiError
import com.tillagewireless.ss.ui.snackbar
import com.tillagewireless.ss.ui.visible
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SetPasswordFragment : Fragment(R.layout.fragment_set_password) {
    private lateinit var binding: FragmentSetPasswordBinding
    private val viewModel by activityViewModels<AuthViewModel>()

    private var navController: NavController? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        binding = FragmentSetPasswordBinding.bind(view)
        binding.progressbar.visible(false)
        binding.btSetPassword.enable(false)

        viewModel.setPasswordResponse.observe(viewLifecycleOwner, Observer {
            binding.progressbar.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    requireView().snackbar("${it.value.status}")
                    viewModel.clearSetPasswordResponse()
                    navController!!.navigate(R.id.action_setPasswordFragment_to_loginFragment)
                }
                is Resource.Failure -> handleApiError(it)
                else -> {}
            }
        })

        binding.etEnterOtp.addTextChangedListener {
            val password = binding.etPassword.text.toString().trim()
            val confPassword = binding.etCoinfirmPassword.text.toString().trim()
            if (it != null) {
                if (it.length < 4) {
                    binding.btSetPassword.enable(false)
                } else {
                    binding.btSetPassword.enable(password.isNotEmpty() && confPassword.isNotEmpty())
                }
            }
        }

        binding.etPassword.addTextChangedListener {
            val otp = binding.etEnterOtp.text.toString().trim()
            val confPassword = binding.etCoinfirmPassword.text.toString().trim()
            if (it != null) {
                if (it.length < 8) {
                    binding.btSetPassword.enable(false)
                } else {
                    binding.btSetPassword.enable(otp.isNotEmpty() && confPassword.isNotEmpty())
                }
            }
        }

        binding.etCoinfirmPassword.addTextChangedListener {
            val otp = binding.etEnterOtp.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            if (it != null) {
                if (it.length < 8) {
                    binding.btSetPassword.enable(false)
                } else {
                    binding.btSetPassword.enable(otp.isNotEmpty() && password.isNotEmpty())
                }
            }
        }

        binding.btSetPassword.setOnClickListener {
            setPassword()
        }
    }

    private fun setPassword() {
        val otp = binding.etEnterOtp.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        val confPassword = binding.etCoinfirmPassword.text.toString().trim()

        if (password != confPassword) {
            binding.etCoinfirmPassword.error = "Password & Confirm password didn't match"
        } else {
            viewModel.setPassword(otp = otp.toInt(), password = password)
        }
    }
}