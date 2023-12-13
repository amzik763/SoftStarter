package com.tillagewireless.ss.ui.home.device

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.tillagewireless.R
import com.tillagewireless.databinding.FragmentDashboardBinding
import com.tillagewireless.ss.data.UserPreferences
import com.tillagewireless.ss.data.network.Resource
import com.tillagewireless.ss.others.Constants.MOTOR_FAULT
import com.tillagewireless.ss.others.Constants.MOTOR_KICK_START_REQ
import com.tillagewireless.ss.others.Constants.MOTOR_RUN
import com.tillagewireless.ss.others.Constants.MOTOR_STARTING
import com.tillagewireless.ss.others.Constants.MOTOR_START_REQ
import com.tillagewireless.ss.others.Constants.MOTOR_STOP
import com.tillagewireless.ss.others.Constants.MOTOR_STOPING
import com.tillagewireless.ss.others.Constants.MOTOR_STOP_REQ
import com.tillagewireless.ss.others.Constants.PHASE_LOSS_B
import com.tillagewireless.ss.others.Constants.PHASE_LOSS_R
import com.tillagewireless.ss.others.Constants.PHASE_LOSS_Y
import com.tillagewireless.ss.ui.handleApiError
import com.tillagewireless.ss.ui.snackbar
import com.tillagewireless.ss.ui.visible
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.OkHttpClient
import java.math.BigInteger
import java.text.DecimalFormat
import javax.inject.Inject


@AndroidEntryPoint
class DashboardFragment : Fragment(R.layout.fragment_dashboard) {
    @Inject
    lateinit var userPreferences: UserPreferences
    private lateinit var binding: FragmentDashboardBinding
    private val viewModel by activityViewModels<DeviceViewModel>()
    private var motorStatus = MOTOR_FAULT
    private lateinit var timer: CountDownTimer
    private var flag = true;
    private var navController: NavController? = null
    private var isRespWait = false
    private var isDeviceNwError = false
    private var waitTimeoutTimer = 0
    private var networkError = true
    private var networkErrorTimer = 7
    private var networkErrorCheckTimer = 0
    private var pktId = 0L
    private var client: OkHttpClient? = null
    private var voltPhaseR = 0
    private var voltPhaseY = 0
    private var voltPhaseB = 0
    private var ampPhaseR = 0
    private var ampPhaseY = 0
    private var ampPhaseB = 0


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDashboardBinding.bind(view)
        navController = Navigation.findNavController(view)
        client = OkHttpClient()

        val speedometer = binding.etVolt
        timer = object : CountDownTimer(600000, 100) {
            override fun onTick(millisUntilFinished: Long) {
                networkErrorCheckTimer++
                if (networkErrorCheckTimer >= 10) {
                    networkErrorCheckTimer = 0
                    checkDeviceNwError()
                }
                updateMotorStatus()
            }
            override fun onFinish() {
                timer.start()
            }
        }
        timer.start()

        userPreferences.getPacketId.asLiveData().observe(viewLifecycleOwner){
            it?.apply {
                if (it != pktId) {
                    networkError = false
                    networkErrorTimer = 0
                    Log.v(TAG, "network id $it .....$pktId")
                    pktId = it
                }
            }
        }

        userPreferences.getMotorFaults.asLiveData().observe(viewLifecycleOwner) {
            it?.apply {

                if (BigInteger.valueOf(it).testBit(PHASE_LOSS_R)) {
                    binding.ivPhaseR.visible(false)
                } else {
                    binding.ivPhaseR.visible(true)
                }
                if (BigInteger.valueOf(it).testBit(PHASE_LOSS_Y)) {
                    binding.ivPhaseY.visible(false)
                } else {
                    binding.ivPhaseY.visible(true)
                }
                if (BigInteger.valueOf(it).testBit(PHASE_LOSS_B)) {
                    binding.ivPhaseB.visible(false)
                } else {
                    binding.ivPhaseB.visible(true)
                }
            }
        }
        userPreferences.getAmpPhaseR.asLiveData().observe(viewLifecycleOwner) {
            it?.apply {
                ampPhaseR = this
            }
        }
        userPreferences.getAmpPhaseY.asLiveData().observe(viewLifecycleOwner) {
            it?.apply {
                ampPhaseY = this
            }
        }
        userPreferences.getAmpPhaseB.asLiveData().observe(viewLifecycleOwner) {
            it?.apply {
                ampPhaseB = this
            }
            speedometer.setCurrentNumber(((ampPhaseR + ampPhaseY + ampPhaseB)/3).toFloat())
        }
        userPreferences.getStarterTemperature.asLiveData().observe(viewLifecycleOwner) {
            it?.apply {
                binding.tvFreqency.text = it.toString()
            }
        }
        userPreferences.getVoltagePhaseR.asLiveData().observe(viewLifecycleOwner) {
            it?.apply {
                voltPhaseR = this
            }
        }
        userPreferences.getVoltagePhaseY.asLiveData().observe(viewLifecycleOwner) {
            it?.apply {
                voltPhaseY = this
            }
        }
        userPreferences.getVoltagePhaseB.asLiveData().observe(viewLifecycleOwner) {
            it?.apply {
                voltPhaseB = this
            }
            binding.tvVoltage.text = ((voltPhaseR + voltPhaseY + voltPhaseB)/3).toString()
        }
        userPreferences.getEnergyUnits.asLiveData().observe(viewLifecycleOwner) {
            it?.apply {
                binding.tvEnergy.updateSpeed(it.toInt())
            }
        }
        userPreferences.getMotorHorsePower.asLiveData().observe(viewLifecycleOwner) {
            it?.apply {
                val dec = DecimalFormat("#0.0")
                binding.etWattage.text = dec.format(it / 1000.0)
            }
        }
        userPreferences.getPowerFactor.asLiveData().observe(viewLifecycleOwner) {
            it?.apply {
                val dec = DecimalFormat("#0.00")
                if (it < 45) {
                    binding.etPf.setTextColor(Color.parseColor("#FF0000"))
                } else {
                    binding.etPf.setTextColor(Color.parseColor("#90EE90"))
                }
                if (it < 100) {
                    binding.etPf.text = dec.format(it / 100.0)
                } else {
                    binding.etPf.text = 1.toString()
                }
            }
        }
        userPreferences.getMotorStatus.asLiveData().observe(viewLifecycleOwner) {
            it?.apply {
                if (motorStatus != it) {
                    isRespWait = false
                    waitTimeoutTimer = 0
                }
                motorStatus = it
            }
        }
        viewModel.requestResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    if (it.value.status == "true") {
                        viewModel.clearRequestResponse()
                        requireView().snackbar("Command success!")
                    }
                }
                is Resource.Failure -> handleApiError(it)
                else -> {}
            }
        }

        binding.kickSwitch.setOnCheckedChangeListener { _, _ ->
            if (motorStatus != MOTOR_STOP || isDeviceNwError || isRespWait) {
                binding.kickSwitch.isChecked = false
            }
        }

        binding.ivOnButton.setOnClickListener {
            if (motorStatus != MOTOR_STOPING) {
                binding.ivOnButton.visible(false)
                binding.ivOffButton.visible(false)
                binding.ivErrorButton.visible(false)
                binding.ivWaitButton.visible(true)
                binding.kickSwitch.isChecked = false
                isRespWait = true
                viewModel.sendCommandToMotor(MOTOR_STOP_REQ,"NA",0)
            }
        }

        binding.ivOffButton.setOnClickListener {
            if (motorStatus != MOTOR_STARTING) {
                binding.ivOnButton.visible(false)
                binding.ivOffButton.visible(false)
                binding.ivErrorButton.visible(false)
                binding.ivWaitButton.visible(true)
                isRespWait = true;
                if (binding.kickSwitch.isChecked) {
                    binding.kickSwitch.isChecked = false
                    viewModel.sendCommandToMotor(MOTOR_KICK_START_REQ, "NA",0)
                } else {
                    viewModel.sendCommandToMotor(MOTOR_START_REQ, "NA",0)
                }
            }
        }
        binding.ivErrorButton.setOnClickListener {
            navController!!.navigate(R.id.action_dashboardFragment_to_trackFragment)
        }
    }

    private fun checkDeviceNwError() {
        if (networkError) {
            networkErrorTimer++
            if ((motorStatus == MOTOR_STOP) || (motorStatus == MOTOR_FAULT)) {
                if (networkErrorTimer >= 122) {
                    networkErrorTimer = 122
                    isDeviceNwError = networkError
                }
            } else {
                if (networkErrorTimer >= 32) {
                    networkErrorTimer = 32
                    isDeviceNwError = networkError
                }
            }
        } else {
            networkErrorTimer = 0
            isDeviceNwError = false
        }
        networkError = true
       // Log.v(TAG, "network error $isDeviceNwError .....$networkErrorTimer")
    }

    private fun updateMotorStatus() {
        if (isDeviceNwError && motorStatus != MOTOR_FAULT) {
            binding.etMotorStatus.text = "NOT CONNECTED"
            binding.etMotorStatus.setTextColor(Color.parseColor("#FFFF00"))
            binding.ivWaitButton.visible(false)
            binding.ivOnButton.visible(false)
            binding.ivOffButton.visible(false)
            binding.ivErrorButton.visible(false)
            binding.ivNwErrButton.visible(true)
        } else if (isRespWait) {
            binding.etMotorStatus.text = "WAIT"
            binding.etMotorStatus.setTextColor(Color.parseColor("#D3D3D3"))
            binding.ivWaitButton.visible(true)
            binding.ivOnButton.visible(false)
            binding.ivOffButton.visible(false)
            binding.ivErrorButton.visible(false)
            binding.ivNwErrButton.visible(false)
            waitTimeoutTimer++
            if (waitTimeoutTimer >= 50) {
                waitTimeoutTimer = 0
                isRespWait = false
            }
        } else {
            binding.ivNwErrButton.visible(false)
            binding.ivWaitButton.visible(false)
            when (motorStatus) {
                MOTOR_STOP -> {
                    binding.etMotorStatus.setTextColor(Color.parseColor("#A31003"))
                    binding.etMotorStatus.text = "MOTOR STOP"
                    binding.ivOnButton.visible(false)
                    binding.ivOffButton.visible(true)
                    binding.ivErrorButton.visible(false)
                }
                MOTOR_STOPING -> {
                    binding.etMotorStatus.setTextColor(Color.parseColor("#A31003"))
                    binding.etMotorStatus.text = "MOTOR STOPING"
                    binding.ivOnButton.visible(false)
                    binding.ivErrorButton.visible(false)
                    if (flag) {
                        binding.ivOffButton.visible(true)
                        flag = false;
                    } else {
                        binding.ivOffButton.visible(false)
                        flag = true
                    }
                }
                MOTOR_RUN -> {
                    binding.etMotorStatus.setTextColor(Color.parseColor("#13A303"))
                    binding.etMotorStatus.text = "MOTOR RUNING"
                    binding.ivOnButton.visible(true)
                    binding.ivOffButton.visible(false)
                    binding.ivErrorButton.visible(false)
                }
                MOTOR_STARTING -> {
                    binding.etMotorStatus.setTextColor(Color.parseColor("#13A303"))
                    binding.etMotorStatus.text = "MOTOR STARTING"
                    binding.ivOffButton.visible(false)
                    binding.ivErrorButton.visible(false)
                    if (flag) {
                        binding.ivOnButton.visible(true)
                        flag = false;
                    } else {
                        binding.ivOnButton.visible(false)
                        flag = true
                    }
                }
                MOTOR_FAULT -> {
                    binding.etMotorStatus.setTextColor(Color.parseColor("#1833EC"))
                    binding.etMotorStatus.text = "MOTOR FAULT"
                    binding.ivOnButton.visible(false)
                    binding.ivOffButton.visible(false)
                    binding.ivErrorButton.visible(true)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.v(TAG, "Dashboard fragment destroyed")
        timer.cancel()
        client!!.dispatcher.executorService.shutdown()
    }
    override fun onPause() {
        super.onPause()
        timer.cancel()
        Log.v(TAG, "Dashboard fragment paused")
    }

    override fun onResume() {
        super.onResume()
        timer.start()
        Log.v(TAG, "Dashboard fragment resumed")
    }
    companion object {
        const val TAG = "DashboardFragment"
    }
}