package com.tillagewireless.ss.ui.home.user

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.tillagewireless.R
import com.tillagewireless.databinding.FragmentSetDeviceBinding
import com.tillagewireless.ss.data.network.Resource
import com.tillagewireless.ss.ui.enable
import com.tillagewireless.ss.ui.handleApiError
import com.tillagewireless.ss.ui.snackbar
import com.tillagewireless.ss.ui.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SetDeviceFragment : Fragment(R.layout.fragment_set_device) {

    private val TAG = "SetDeviceFragment"

    private lateinit var binding: FragmentSetDeviceBinding
    private val viewModel by activityViewModels<UserViewModel>()
    private var isSetDefault = false
    private var deviceId = ""
    private var navController: NavController? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        binding = FragmentSetDeviceBinding.bind(view)
        binding.progressbar.visible(false)
        binding.btVerifyOwnership.enable(false)

        viewModel.registeringDeviceId.observe(viewLifecycleOwner, Observer {
            deviceId = it
        })

        viewModel.registeringIsDefault.observe(viewLifecycleOwner, Observer {
            isSetDefault = it
        })

        viewModel.registerDeviceVerifyResponse.observe(viewLifecycleOwner, Observer {
            binding.progressbar.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (isSetDefault) {
                        lifecycleScope.launch {
                            viewModel.setDefaultDevice(deviceId)
                        }
                        requireView().snackbar("Registration success, restarting app for Device: $deviceId")
                        Log.d(TAG, "Registration success, restarting app for Device: $deviceId")
                        viewModel.clearRegisterDeviceVerifyResponse()
                        viewModel.startNewActivity("MAIN")
                    } else {
                        requireView().snackbar("Registration success")
                        Log.d(TAG, "Registration success")
                        viewModel.clearRegisterDeviceVerifyResponse()
                        navController!!.navigate(R.id.action_setDeviceFragment_to_registerVehicleFragment)
                    }
                }
                is Resource.Failure -> handleApiError(it)
                else -> {}
            }
        })
        binding.etEnterOtp.addTextChangedListener {
            if (it != null) {
                if (it.length < 3) {
                    binding.btVerifyOwnership.enable(false)
                } else {
                    binding.btVerifyOwnership.enable(true)
                }
            }
        }

        binding.btVerifyOwnership.setOnClickListener {
            registerDeviceVerify()
        }

        binding.tvCancelOtp.setOnClickListener {
            navController!!.navigate(R.id.action_setDeviceFragment_to_registerVehicleFragment)
        }
    }

    private fun registerDeviceVerify() {
        val otp = binding.etEnterOtp.text.toString().trim()
        viewModel.registerDeviceVerify(otp.toInt(), deviceId)
    }
}