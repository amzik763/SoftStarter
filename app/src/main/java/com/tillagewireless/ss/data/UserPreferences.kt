package com.tillagewireless.ss.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "my_data_store")

class UserPreferences @Inject constructor(context: Context) {

    private val appContext = context.applicationContext

    val accessToken: Flow<String?>
        get() = appContext.dataStore.data.map { preferences ->
            preferences[ACCESS_TOKEN]
        }
    
    val refreshToken: Flow<String?>
        get() = appContext.dataStore.data.map { preferences ->
            preferences[REFRESH_TOKEN]
        }

    val fcmToken: Flow<String?>
        get() = appContext.dataStore.data.map { preferences ->
            preferences[FCM_TOKEN]
        }

    val getDefaultDeviceId: Flow<String?>
        get() = appContext.dataStore.data.map { preferences ->
            preferences[DEFAULT_DEVICE_ID]
        }
    val getPacketId: Flow<Long?>
        get() = appContext.dataStore.data.map { preferences ->
            preferences[PACKET_ID]
        }
    val getServerTimestamp: Flow<String?>
        get() = appContext.dataStore.data.map { preferences ->
            preferences[SERVER_TIME_STAMP]
        }
    val getDeviceTimestamp: Flow<String?>
        get() = appContext.dataStore.data.map { preferences ->
            preferences[DEVICE_TIME_STAMP]
        }
    val getDeviceId: Flow<String?>
        get() = appContext.dataStore.data.map { preferences ->
            preferences[DEVICE_ID]
        }
    val getModemSwVer: Flow<Int?>
        get() = appContext.dataStore.data.map { preferences ->
            preferences[MODEM_SW_VERSION]
        }
    val getMcuSwVer: Flow<Int?>
        get() = appContext.dataStore.data.map { preferences ->
            preferences[MCU_SW_VERSION]
        }
    val getMcuHwVer: Flow<Int?>
        get() = appContext.dataStore.data.map { preferences ->
            preferences[MCU_HW_VERSION]
        }
    val getStarterConfigStatus: Flow<Int?>
        get() = appContext.dataStore.data.map { preferences ->
            preferences[STARTER_CONFIG]
        }
    val getSignalStrength: Flow<Int?>
        get() = appContext.dataStore.data.map { preferences ->
            preferences[SIGNAL_STRENGTH]
        }
    val getBatVoltage: Flow<Int?>
        get() = appContext.dataStore.data.map { preferences ->
            preferences[BAT_VOLTAGE]
        }
    val getStartLockStatus: Flow<Boolean?>
        get() = appContext.dataStore.data.map { preferences ->
            preferences[START_LOCK_STATUS]
        }
    val getAutoStartStatus: Flow<Boolean?>
        get() = appContext.dataStore.data.map { preferences ->
            preferences[AUTO_START_STATUS]
        }
    val getAutoStopStatus: Flow<Boolean?>
        get() = appContext.dataStore.data.map { preferences ->
            preferences[AUTO_STOP_STATUS]
        }
    val getAutoStopTimer: Flow<Int?>
        get() = appContext.dataStore.data.map { preferences ->
            preferences[AUTO_STOP_TIMER]
        }
    val getDryRunBypassStatus: Flow<Boolean?>
        get() = appContext.dataStore.data.map { preferences ->
            preferences[DRY_RUN_BYPASS]
        }
    val getMotorStatus: Flow<Int?>
        get() = appContext.dataStore.data.map { preferences ->
            preferences[MOTOR_STATUS]
        }
    val getNumberOfStarts: Flow<Int?>
        get() = appContext.dataStore.data.map { preferences ->
            preferences[NUMBER_OF_STARTS]
        }
    val getMotorFaults: Flow<Long?>
        get() = appContext.dataStore.data.map { preferences ->
            preferences[MOTOR_FAULTS]
        }
    val getStarterFaults: Flow<Long?>
        get() = appContext.dataStore.data.map { preferences ->
            preferences[STARTER_FAULTS]
        }
    val getVoltagePhaseR: Flow<Int?>
        get() = appContext.dataStore.data.map { preferences ->
            preferences[VOLT_PHASE_R]
        }
    val getVoltagePhaseY: Flow<Int?>
        get() = appContext.dataStore.data.map { preferences ->
            preferences[VOLT_PHASE_Y]
        }
    val getVoltagePhaseB: Flow<Int?>
        get() = appContext.dataStore.data.map { preferences ->
            preferences[VOLT_PHASE_B]
        }
    val getAmpPhaseR: Flow<Int?>
        get() = appContext.dataStore.data.map { preferences ->
            preferences[AMP_PHASE_R]
        }
    val getAmpPhaseY: Flow<Int?>
        get() = appContext.dataStore.data.map { preferences ->
            preferences[AMP_PHASE_Y]
        }
    val getAmpPhaseB: Flow<Int?>
        get() = appContext.dataStore.data.map { preferences ->
            preferences[AMP_PHASE_B]
        }
    val getMotorHorsePower: Flow<Int?>
        get() = appContext.dataStore.data.map { preferences ->
            preferences[MOTOR_HORSE_POWER]
        }
    val getEnergyUnits: Flow<Int?>
        get() = appContext.dataStore.data.map { preferences ->
            preferences[ENERGY_UNITS]
        }
    val getPowerFactor: Flow<Int?>
        get() = appContext.dataStore.data.map { preferences ->
            preferences[POWER_FACTOR]
        }
    val getFrequencyPhaseR: Flow<Int?>
        get() = appContext.dataStore.data.map { preferences ->
            preferences[FREQUENCY_PHASE_R]
        }
    val getFrequencyPhaseY: Flow<Int?>
        get() = appContext.dataStore.data.map { preferences ->
            preferences[FREQUENCY_PHASE_Y]
        }
    val getFrequencyPhaseB: Flow<Int?>
        get() = appContext.dataStore.data.map { preferences ->
            preferences[FREQUENCY_PHASE_B]
        }
    val getPhaseAngleR: Flow<Int?>
        get() = appContext.dataStore.data.map { preferences ->
            preferences[PHASE_ANGLE_R]
        }
    val getPhaseAngleY: Flow<Int?>
        get() = appContext.dataStore.data.map { preferences ->
            preferences[PHASE_ANGLE_Y]
        }
    val getPhaseAngleB: Flow<Int?>
        get() = appContext.dataStore.data.map { preferences ->
            preferences[PHASE_ANGLE_B]
        }
    val getStarterTemperature: Flow<Int?>
        get() = appContext.dataStore.data.map { preferences ->
            preferences[STARTER_TEMPERATURE]
        }

    suspend fun saveAccessTokens(accessToken: String, refreshToken: String) {
        appContext.dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN] = accessToken
	        preferences[REFRESH_TOKEN] = refreshToken
        }
    }
    suspend fun saveFcmToken(token: String) {
        appContext.dataStore.edit { preferences ->
            preferences[FCM_TOKEN] = token
        }
    }
    suspend fun saveDefaultDeviceId(defaultDeviceId: String) {
        appContext.dataStore.edit { preferences ->
            preferences[DEFAULT_DEVICE_ID] = defaultDeviceId
        }
    }
    suspend fun clearAccessToken() {
        appContext.dataStore.edit { preferences ->
            preferences.remove(ACCESS_TOKEN)
            preferences.remove(REFRESH_TOKEN)
        }
    }
    suspend fun savePacketId(pid: Long) {
        appContext.dataStore.edit { preferences ->
            preferences[PACKET_ID] = pid
        }
    }
    suspend fun saveServerTimestamp(ts: String) {
        appContext.dataStore.edit { preferences ->
            preferences[SERVER_TIME_STAMP] = ts
        }
    }
    suspend fun saveDeviceTimestamp(ts: String) {
        appContext.dataStore.edit { preferences ->
            preferences[DEVICE_TIME_STAMP] = ts
        }
    }
    suspend fun saveDeviceId(ts: String) {
        appContext.dataStore.edit { preferences ->
            preferences[DEVICE_ID] = ts
        }
    }
    suspend fun saveModemSwVersion(ver: Int) {
        appContext.dataStore.edit { preferences ->
            preferences[MODEM_SW_VERSION] = ver
        }
    }
    suspend fun saveMcuSwVersion(ver: Int) {
        appContext.dataStore.edit { preferences ->
            preferences[MCU_SW_VERSION] = ver
        }
    }
    suspend fun saveMcuHwVersion(ver: Int) {
        appContext.dataStore.edit { preferences ->
            preferences[MCU_HW_VERSION] = ver
        }
    }
    suspend fun saveStarterConfigStatus(value: Int) {
        appContext.dataStore.edit { preferences ->
            preferences[STARTER_CONFIG] = value
        }
    }
    suspend fun saveSignalStrength(value: Int) {
        appContext.dataStore.edit { preferences ->
            preferences[SIGNAL_STRENGTH] = value
        }
    }
    suspend fun saveBatVoltage(value: Int) {
        appContext.dataStore.edit { preferences ->
            preferences[BAT_VOLTAGE] = value
        }
    }
    suspend fun saveStartLockStatus(status: Boolean) {
        appContext.dataStore.edit { preferences ->
            preferences[START_LOCK_STATUS] = status
        }
    }
    suspend fun saveAutoStartStatus(status: Boolean) {
        appContext.dataStore.edit { preferences ->
            preferences[AUTO_START_STATUS] = status
        }
    }
    suspend fun saveAutoStopStatus(status: Boolean) {
        appContext.dataStore.edit { preferences ->
            preferences[AUTO_STOP_STATUS] = status
        }
    }
    suspend fun saveAutoStopTimer(value: Int) {
        appContext.dataStore.edit { preferences ->
            preferences[AUTO_STOP_TIMER] = value
        }
    }
    suspend fun saveDryRunBypassStatus(status: Boolean) {
        appContext.dataStore.edit { preferences ->
            preferences[DRY_RUN_BYPASS] = status
        }
    }
    suspend fun saveMotorStatus(value: Int) {
        appContext.dataStore.edit { preferences ->
            preferences[MOTOR_STATUS] = value
        }
    }
    suspend fun saveNumberOfStarts(value: Int) {
        appContext.dataStore.edit { preferences ->
            preferences[NUMBER_OF_STARTS] = value
        }
    }
    suspend fun saveMotorFaults(value: Long) {
        appContext.dataStore.edit { preferences ->
            preferences[MOTOR_FAULTS] = value
        }
    }
    suspend fun saveStarterFaults(value: Long) {
        appContext.dataStore.edit { preferences ->
            preferences[STARTER_FAULTS] = value
        }
    }
    suspend fun saveVoltagePhaseR(value: Int) {
        appContext.dataStore.edit { preferences ->
            preferences[VOLT_PHASE_R] = value
        }
    }
    suspend fun saveVoltagePhaseY(value: Int) {
        appContext.dataStore.edit { preferences ->
            preferences[VOLT_PHASE_Y] = value
        }
    }
    suspend fun saveVoltagePhaseB(value: Int) {
        appContext.dataStore.edit { preferences ->
            preferences[VOLT_PHASE_B] = value
        }
    }
    suspend fun saveAmpPhaseR(value: Int) {
        appContext.dataStore.edit { preferences ->
            preferences[AMP_PHASE_R] = value
        }
    }
    suspend fun saveAmpPhaseY(value: Int) {
        appContext.dataStore.edit { preferences ->
            preferences[AMP_PHASE_Y] = value
        }
    }
    suspend fun saveAmpPhaseB(value: Int) {
        appContext.dataStore.edit { preferences ->
            preferences[AMP_PHASE_B] = value
        }
    }
    suspend fun saveMotorHorsePower(value: Int) {
        appContext.dataStore.edit { preferences ->
            preferences[MOTOR_HORSE_POWER] = value
        }
    }
    suspend fun saveEnergyUnits(value: Int) {
        appContext.dataStore.edit { preferences ->
            preferences[ENERGY_UNITS] = value
        }
    }
    suspend fun savePowerFactor(value: Int) {
        appContext.dataStore.edit { preferences ->
            preferences[POWER_FACTOR] = value
        }
    }
    suspend fun saveFrequencyPhaseR(value: Int) {
        appContext.dataStore.edit { preferences ->
            preferences[FREQUENCY_PHASE_R] = value
        }
    }
    suspend fun saveFrequencyPhaseY(value: Int) {
        appContext.dataStore.edit { preferences ->
            preferences[FREQUENCY_PHASE_Y] = value
        }
    }
    suspend fun saveFrequencyPhaseB(value: Int) {
        appContext.dataStore.edit { preferences ->
            preferences[FREQUENCY_PHASE_B] = value
        }
    }
    suspend fun savePhaseAngleR(value: Int) {
        appContext.dataStore.edit { preferences ->
            preferences[PHASE_ANGLE_R] = value
        }
    }
    suspend fun savePhaseAngleY(value: Int) {
        appContext.dataStore.edit { preferences ->
            preferences[PHASE_ANGLE_Y] = value
        }
    }
    suspend fun savePhaseAngleB(value: Int) {
        appContext.dataStore.edit { preferences ->
            preferences[PHASE_ANGLE_B] = value
        }
    }
    suspend fun saveStarterTemperature(value: Int) {
        appContext.dataStore.edit { preferences ->
            preferences[STARTER_TEMPERATURE] = value
        }
    }

    companion object {
        private val ACCESS_TOKEN = stringPreferencesKey("key_access_token")
	    private val REFRESH_TOKEN = stringPreferencesKey("key_refresh_token")
        private val FCM_TOKEN  = stringPreferencesKey("key_fcm_token")
        private val DEFAULT_DEVICE_ID  = stringPreferencesKey("key_default_device_id")
        private val PACKET_ID = longPreferencesKey("key_packet_id") // TODO: change to Long
        private val SERVER_TIME_STAMP  = stringPreferencesKey("key_server_time_stamp")
        private val DEVICE_TIME_STAMP  = stringPreferencesKey("key_device_time_stamp")
        private val DEVICE_ID  = stringPreferencesKey("key_device_id")
        private val MODEM_SW_VERSION  = intPreferencesKey("key_modem_sw_version")
        private val MCU_SW_VERSION  = intPreferencesKey("key_mcu_sw_version")
        private val MCU_HW_VERSION  = intPreferencesKey("key_mcu_hw_version")
        private val STARTER_CONFIG  = intPreferencesKey("key_starter_config_status")
        private val SIGNAL_STRENGTH  = intPreferencesKey("key_signal_strength")
        private val BAT_VOLTAGE  = intPreferencesKey("key_bat_voltage")
        private val START_LOCK_STATUS  = booleanPreferencesKey("key_start_lock_status")
        private val AUTO_START_STATUS  = booleanPreferencesKey("key_auto_start_status")
        private val AUTO_STOP_STATUS  = booleanPreferencesKey("key_auto_stop_status")
        private val AUTO_STOP_TIMER  = intPreferencesKey("key_auto_stop_timer")
        private val DRY_RUN_BYPASS  = booleanPreferencesKey("key_dry_run_bypass")
        private val MOTOR_STATUS  = intPreferencesKey("key_motor_status")
        private val NUMBER_OF_STARTS = intPreferencesKey("key_number_of_starts")
        private val MOTOR_FAULTS = longPreferencesKey("key_motor_faults")
        private val STARTER_FAULTS = longPreferencesKey("key_starter_faults")
        private val VOLT_PHASE_R = intPreferencesKey("key_volt_phase_r")
        private val VOLT_PHASE_Y = intPreferencesKey("key_volt_phase_y")
        private val VOLT_PHASE_B = intPreferencesKey("key_volt_phase_b")
        private val AMP_PHASE_R = intPreferencesKey("key_amp_phase_r")
        private val AMP_PHASE_Y = intPreferencesKey("key_amp_phase_y")
        private val AMP_PHASE_B = intPreferencesKey("key_amp_phase_b")
        private val MOTOR_HORSE_POWER = intPreferencesKey("key_motor_horse_power")
        private val ENERGY_UNITS = intPreferencesKey("key_energy_units")
        private val POWER_FACTOR = intPreferencesKey("key_power_factor")
        private val FREQUENCY_PHASE_R = intPreferencesKey("key_frequency_phase_r")
        private val FREQUENCY_PHASE_Y = intPreferencesKey("key_frequency_phase_y")
        private val FREQUENCY_PHASE_B = intPreferencesKey("key_frequency_phase_b")
        private val PHASE_ANGLE_R = intPreferencesKey("key_phase_angle_r")
        private val PHASE_ANGLE_Y = intPreferencesKey("key_phase_angle_y")
        private val PHASE_ANGLE_B = intPreferencesKey("key_phase_angle_b")
        private val STARTER_TEMPERATURE = intPreferencesKey("key_starter_temperature")
    }
}