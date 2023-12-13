package com.tillagewireless.ss.ui.home.user

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.tillagewireless.R
import com.tillagewireless.databinding.FragmentSettingsBinding
import com.tillagewireless.ss.configuration
import com.tillagewireless.ss.data.UserPreferences
import com.tillagewireless.ss.data.network.Resource
import com.tillagewireless.ss.ui.enable
import com.tillagewireless.ss.ui.handleApiError
import com.tillagewireless.ss.ui.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment: Fragment(R.layout.fragment_settings){
    @Inject
    lateinit var userPreferences: UserPreferences
    private lateinit var binding: FragmentSettingsBinding
    private val viewModelUser by activityViewModels<UserViewModel>()
    private var navController: NavController? = null
    private var selectedDeviceId: String = ""
    private var devicesNeme = mutableListOf<String>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        binding = FragmentSettingsBinding.bind(view)
        binding.progressbar.visible(false)
        binding.tvDeviceId.text = configuration.deviceId
        binding.btChangeDevice.enable(false)

        viewModelUser.getDevices()

        viewModelUser.devices.observe(viewLifecycleOwner, Observer {
            binding.progressbar.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    lifecycleScope.launch {
                        viewModelUser.saveDevices(it.value)
                    }
                }
                is Resource.Failure -> handleApiError(it)
                else -> {}
            }
        })

        viewModelUser.getDevicesId().observe(viewLifecycleOwner, Observer {
            devicesNeme = it as MutableList<String>
            devicesNeme.add(0,"Change Device")
            val adapter = activity?.let {
                ArrayAdapter<String>(
                    it,
                    android.R.layout.simple_spinner_item,
                    devicesNeme
                )
            }
            binding.selectDevice.adapter = adapter
        })

        binding.selectDevice.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedDeviceId = parent?.getItemAtPosition(position).toString()
                if (selectedDeviceId != "Change Device"){
                    binding.btChangeDevice.enable(true)
                }else{
                    binding.btChangeDevice.enable(false)
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
        userPreferences.getDefaultDeviceId.asLiveData().observe(viewLifecycleOwner, Observer {
            it?.apply {
                configuration.deviceId = it
                binding.tvDeviceId.text = configuration.deviceId
            }
        })
        binding.btChangeDevice.setOnClickListener {
            runBlocking {
                viewModelUser.setDefaultDevice(selectedDeviceId)
                viewModelUser.saveLastSavedPacketId(0)
                Log.d(TAG, "Configuration data updated!!")
            }
            Log.d(TAG, "Applying new Configuration restarting MAIN")
            viewModelUser.startNewActivity("MAIN")
        }
    }
    companion object{
        const val TAG = "SettingFragment"
    }
}
