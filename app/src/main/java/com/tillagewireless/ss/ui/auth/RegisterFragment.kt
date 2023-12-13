package com.tillagewireless.ss.ui.auth

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.tillagewireless.R
import com.tillagewireless.databinding.FragmentRegisterBinding
import com.tillagewireless.ss.data.network.Resource
import com.tillagewireless.ss.ui.*
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class RegisterFragment : Fragment(R.layout.fragment_register) {
    private lateinit var binding: FragmentRegisterBinding
    private val viewModel by activityViewModels<AuthViewModel>()

    private var navController: NavController? = null

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        binding = FragmentRegisterBinding.bind(view)
        binding.progressbar.visible(false)
        binding.btUserRegister.enable(false)

        viewModel.registerResponse.observe(viewLifecycleOwner, Observer {
            binding.progressbar.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    requireView().snackbar("${it.value.status}")
                    viewModel.clearRegisterResponse()
                    navController!!.navigate(R.id.action_registrationFragment_to_setPasswordFragment)
                }
                is Resource.Failure -> handleApiError(it) { register() }
                else -> {}
            }
        })

        binding.etMobile.addTextChangedListener {
            val name = binding.etFullName.text.toString().trim()
            if (it != null) {
                if (it.length < 10) {
                    binding.btUserRegister.enable(false)
                } else {
                    binding.btUserRegister.enable(name.isNotEmpty())
                }
            }
        }

        binding.etFullName.addTextChangedListener {
            val mobile = binding.etMobile.text.toString().trim()
            if (it != null) {
                if (it.length < 3) {
                    binding.btUserRegister.enable(false)
                } else {
                    binding.btUserRegister.enable(mobile.isNotEmpty())
                }
            }
        }


        binding.btUserRegister.setOnClickListener {
            register()
        }
        binding.tvLoginNow.setOnClickListener {
            viewModel.clearRegisterResponse()
            navController!!.navigate(R.id.action_registrationFragment_to_loginFragment)
        }

        binding.etDateOfBirth.transformIntoDatePicker(requireContext(), "MM/dd/yyyy")

        binding.etDateOfBirth.setOnClickListener {
            //  binding.etDateOfBirth.transformIntoDatePicker(requireContext(), "MM/dd/yyyy")
            binding.etDateOfBirth.transformIntoDatePicker(
                requireContext(),
                "MM/dd/yyyy",
                Date()
            )
        }
    }

    private fun register() {
        val name = binding.etFullName.text.toString().trim()
        val mobile = "+91${binding.etMobile.text.toString().trim()}"
        val dob = binding.etDateOfBirth.text.toString().trim()

        val firstName = name.substringBeforeLast(" ")
        var lastName = name.substringAfterLast(" ")

        if (firstName == lastName) {
            lastName = ""
        }
        viewModel.register(firstName, lastName, mobile, dob)
    }
}