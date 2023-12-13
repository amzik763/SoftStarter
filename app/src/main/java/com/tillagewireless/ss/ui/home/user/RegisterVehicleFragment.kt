package com.tillagewireless.ss.ui.home.user

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.tillagewireless.R
import com.tillagewireless.databinding.FragmentRegisterVehicleBinding
import com.tillagewireless.ss.data.network.Resource
import com.tillagewireless.ss.ui.enable
import com.tillagewireless.ss.ui.handleApiError
import com.tillagewireless.ss.ui.snackbar
import com.tillagewireless.ss.ui.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterVehicleFragment : Fragment(R.layout.fragment_register_vehicle) {

    private val TAG = "RegisterVehicleFragment"

    private lateinit var binding: FragmentRegisterVehicleBinding
    private val viewModel by activityViewModels<UserViewModel>()

    private var navController: NavController? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        binding = FragmentRegisterVehicleBinding.bind(view)
        binding.progressbar.visible(false)
        binding.btGetOtp.enable(false)

        viewModel.registerDeviceResponse.observe(viewLifecycleOwner, Observer {
            binding.progressbar.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    requireView().snackbar("${it.value.status}")
                    viewModel.clearRegisterDeviceResponse()
                    Log.d(TAG,"Response: ${it.value.deviceId}")
                    navController!!.navigate(R.id.action_registerVehicleFragment_to_setDeviceFragment)

                }
                is Resource.Failure -> handleApiError(it)
                else -> {}
            }
        })

        binding.etDeviceId.addTextChangedListener {
            val deviceId = binding.etDeviceId.text.toString().trim()
            if (it != null) {
                if (it.length < 15) {
                    binding.btGetOtp.enable(false)
                } else {
                    binding.btGetOtp.enable(deviceId.isNotEmpty())
                }
            }
        }

        binding.btGetOtp.setOnClickListener {
            registerVehicle()
        }
    }

    private fun registerVehicle() {
        val deviceId:String = binding.etDeviceId.text.toString().trim()
        val deviceName:String = binding.etName.text.toString().trim()
        val isSetDefault:Boolean = binding.checkBoxDefault.isChecked
        viewModel.registerDevice(deviceId=deviceId, deviceName=deviceName, vehicleNumber = "", isSetDefault = isSetDefault)
    }
}