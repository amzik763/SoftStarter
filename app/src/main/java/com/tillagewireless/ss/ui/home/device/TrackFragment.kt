package com.tillagewireless.ss.ui.home.device

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import com.tillagewireless.R
import com.tillagewireless.databinding.FragmentTrackBinding
import com.tillagewireless.ss.data.UserPreferences
import com.tillagewireless.ss.others.Constants
import com.tillagewireless.ss.others.Constants.RESET_DEVICE_REQ
import com.tillagewireless.ss.ui.visible
import dagger.hilt.android.AndroidEntryPoint
import java.math.BigInteger
import javax.inject.Inject


@AndroidEntryPoint
class TrackFragment : Fragment(R.layout.fragment_track) {
    @Inject
    lateinit var userPreferences: UserPreferences
    private lateinit var binding: FragmentTrackBinding
    private val viewModel by activityViewModels<DeviceViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTrackBinding.bind(view)

        binding.ClearFaultButton?.setOnClickListener{
           // viewModel.sendCommandToMotor(Constants.FAULT_CLEAR_REQ,"NA",0)
            viewModel.sendCommandToMotor(Constants.WRITE_DEVICE_CONF_REQ,"FL_CURRENT",260)
        }
        binding.RestStarterButton?.setOnClickListener{
            viewModel.sendCommandToMotor(RESET_DEVICE_REQ,"NA",0)
        }

        userPreferences.getMotorFaults.asLiveData().observe(viewLifecycleOwner){
            it?.apply {
                if (BigInteger.valueOf(it).testBit(Constants.ROTOR_LOCKED)) {
                    binding.rotorLockSet?.visible(true)
                    binding.rotorLockClear?.visible(false)
                } else {
                    binding.rotorLockSet?.visible(false)
                    binding.rotorLockClear?.visible(true)
                }
                if (BigInteger.valueOf(it).testBit(Constants.MOTOR_SC_FAULT)) {
                    binding.motorShortSet?.visible(true)
                    binding.motorShortClear?.visible(false)
                } else {
                    binding.motorShortSet?.visible(false)
                    binding.motorShortClear?.visible(true)
                }
                if (BigInteger.valueOf(it).testBit(Constants.MOTOR_OL_FAULT)) {
                    binding.overLoadSet?.visible(true)
                    binding.overLoadClear?.visible(false)
                } else {
                    binding.overLoadSet?.visible(false)
                    binding.overLoadClear?.visible(true)
                }
                if (BigInteger.valueOf(it).testBit(Constants.DRY_RUN_FAULT)) {
                    binding.dryRunSet?.visible(true)
                    binding.dryRunClear?.visible(false)
                } else {
                    binding.dryRunSet?.visible(false)
                    binding.dryRunClear?.visible(true)
                }
                if (BigInteger.valueOf(it).testBit(Constants.POWER_LOSS)) {
                    binding.powerLossSet?.visible(true)
                    binding.powerLossClear?.visible(false)
                    binding.phaseLossSet?.visible(false)
                    binding.phaseReversalClear?.visible(true)
                } else {
                    binding.powerLossSet?.visible(false)
                    binding.powerLossClear?.visible(true)
                    if (BigInteger.valueOf(it).testBit(Constants.PHASE_LOSS_R)
                        || BigInteger.valueOf(it).testBit(Constants.PHASE_LOSS_Y)
                        || BigInteger.valueOf(it).testBit(Constants.PHASE_LOSS_B)) {
                        binding.phaseLossSet?.visible(true)
                        binding.phaseReversalClear?.visible(false)
                    } else {
                        binding.phaseLossSet?.visible(false)
                        binding.phaseReversalClear?.visible(true)
                    }
                }
                if (BigInteger.valueOf(it).testBit(Constants.OVER_VOLTAGE)) {
                    binding.overVoltageSet?.visible(true)
                    binding.overVoltageClear?.visible(false)
                } else {
                    binding.overVoltageSet?.visible(false)
                    binding.overVoltageClear?.visible(true)
                }
                if (BigInteger.valueOf(it).testBit(Constants.UNDER_VOLTAGE)) {
                    binding.underVoltageSet?.visible(true)
                    binding.underVoltageClear?.visible(false)
                } else {
                    binding.underVoltageSet?.visible(false)
                    binding.underVoltageClear?.visible(true)
                }
                if (BigInteger.valueOf(it).testBit(Constants.FREQ_FAULT)) {
                    binding.wrongFreqSet?.visible(true)
                    binding.wrongFreqClear?.visible(false)
                } else {
                    binding.wrongFreqSet?.visible(false)
                    binding.wrongFreqClear?.visible(true)
                }
                if (BigInteger.valueOf(it).testBit(Constants.PHASE_REVERSAL)) {
                    binding.phaseReversalSet?.visible(true)
                    binding.phaseReversalClear?.visible(false)
                } else {
                    binding.phaseReversalSet?.visible(false)
                    binding.phaseReversalClear?.visible(true)
                }
                if (BigInteger.valueOf(it).testBit(Constants.NO_FIRE)) {
                    binding.noFireSet?.visible(true)
                    binding.noFireClear?.visible(false)
                } else {
                    binding.noFireSet?.visible(false)
                    binding.noFireClear?.visible(true)
                }
                if (BigInteger.valueOf(it).testBit(Constants.WATER_OVERHEAT)) {
                    binding.waterOverheatSet?.visible(true)
                    binding.waterOverheatClear?.visible(false)
                } else {
                    binding.waterOverheatSet?.visible(false)
                    binding.waterOverheatClear?.visible(true)
                }
            }
        }
    }
    companion object {
        const val TAG = "TrackFragment"
    }
}