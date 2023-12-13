package com.tillagewireless.ss.data.repository

import com.tillagewireless.ss.configuration
import com.tillagewireless.ss.data.UserPreferences
import com.tillagewireless.ss.data.db.models.UpdateTopicItem
import com.tillagewireless.ss.data.network.DeviceAPI
import javax.inject.Inject

class DeviceRepository @Inject constructor(
    private val api: DeviceAPI,
    private val userPreferences: UserPreferences
) : BaseRepository(api) {

    suspend fun getUpdateTopicDataApi()
    = safeApiCall {
        api.getUpdateTopicData(configuration.deviceId)
    }

    suspend fun getAlertTopicDataApi()
            = safeApiCall {
        api.getAlertTopicData(configuration.deviceId)
    }

    suspend fun sendCommandToMotor(
        cmd:String,
        subCmd:String,
        cmdData:Int
    )
    = safeApiCall {
        api.sendCommandToMotor(configuration.deviceId, cmd,subCmd,cmdData)
    }

    suspend fun saveUpdateTopicRecord(item: UpdateTopicItem){
        userPreferences.savePacketId(item.id!!)
        userPreferences.saveServerTimestamp(item.ServTimeStemp!!)
        userPreferences.saveDeviceTimestamp(item.TimeStemp!!)
        userPreferences.saveDeviceId(item.DeviceId)
        userPreferences.saveModemSwVersion(item.A9SwVer!!)
        userPreferences.saveMcuSwVersion(item.StmHwVer!!)
        userPreferences.saveMcuHwVersion(item.StmHwVer!!)
        userPreferences.saveStarterConfigStatus(item.Config!!)
        userPreferences.saveSignalStrength(item.Signal!!)
        userPreferences.saveBatVoltage(item.BatVolt!!)
        userPreferences.saveStartLockStatus(item.StartLock!!)
        userPreferences.saveAutoStartStatus(item.AutoStart!!)
        userPreferences.saveAutoStopStatus(item.AutoStop!!)
        userPreferences.saveAutoStopTimer(item.AutoStopTim!!)
        userPreferences.saveDryRunBypassStatus(item.DrunByps!!)
        userPreferences.saveMotorStatus(item.MotorStatus!!)
        userPreferences.saveNumberOfStarts(item.NumOfStart!!)
        userPreferences.saveMotorFaults(item.MotorFault!!)
        userPreferences.saveStarterFaults(item.DeviceFault!!)
        userPreferences.saveVoltagePhaseR(item.VoltR!!)
        userPreferences.saveVoltagePhaseY(item.VoltY!!)
        userPreferences.saveVoltagePhaseB(item.VoltB!!)
        userPreferences.saveAmpPhaseR(item.AmpR!!)
        userPreferences.saveAmpPhaseY(item.AmpY!!)
        userPreferences.saveAmpPhaseB(item.AmpB!!)
        userPreferences.saveMotorHorsePower(item.HP!!)
        userPreferences.saveEnergyUnits(item.Energy!!)
        userPreferences.savePowerFactor(item.PF!!)
        userPreferences.saveFrequencyPhaseR(item.FreqR!!)
        userPreferences.saveFrequencyPhaseY(item.FreqY!!)
        userPreferences.saveFrequencyPhaseB(item.FreqB!!)
        userPreferences.savePhaseAngleR(item.AngleR!!)
        userPreferences.savePhaseAngleY(item.AngleY!!)
        userPreferences.savePhaseAngleB(item.AngleB!!)
        userPreferences.saveStarterTemperature(item.Temp!!)
    }
}