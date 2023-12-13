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
import com.tillagewireless.databinding.FragmentForgetPasswordBinding
import com.tillagewireless.ss.data.network.Resource
import com.tillagewireless.ss.ui.enable
import com.tillagewireless.ss.ui.handleApiError
import com.tillagewireless.ss.ui.snackbar
import com.tillagewireless.ss.ui.visible
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ForgetPasswordFragment : Fragment(R.layout.fragment_forget_password) {
    private lateinit var binding: FragmentForgetPasswordBinding
    private val viewModel by activityViewModels<AuthViewModel>()

    private var navController: NavController? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        binding = FragmentForgetPasswordBinding.bind(view)
        binding.progressbar.visible(false)
        binding.btGetOtp.enable(false)

        viewModel.forgetPasswordResponse.observe(viewLifecycleOwner, Observer {
            binding.progressbar.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    requireView().snackbar("${it.value.status}")
                    viewModel.clearForgetPasswordResponse()
                    navController!!.navigate(R.id.action_forgetPasswordFragment_to_setPasswordFragment)
                }
                is Resource.Failure -> handleApiError(it)
                else -> {}
            }
        })

        binding.etPhoneNumber.addTextChangedListener {
            if (it != null) {
                if (it.length < 10) {
                    binding.btGetOtp.enable(false)
                } else {
                    binding.btGetOtp.enable(true)
                }
            }
        }

        binding.btGetOtp.setOnClickListener {
            requestOtp()
        }
        binding.tvCancelOtp.setOnClickListener {
            viewModel.clearRegisterResponse()
            navController!!.navigate(R.id.action_forgetPasswordFragment_to_loginFragment)
        }
    }

    private fun requestOtp() {
        val mobileNumber = "+91${binding.etPhoneNumber.text.toString().trim()}"
        viewModel.forgetPassword(mobileNumber)
    }
}