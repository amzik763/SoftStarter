package com.tillagewireless.ss.data.repository

import com.tillagewireless.ss.data.UserPreferences
import com.tillagewireless.ss.data.db.DeviceDao
import com.tillagewireless.ss.data.db.UserDao
import com.tillagewireless.ss.data.db.models.Device
import com.tillagewireless.ss.data.db.models.User
import com.tillagewireless.ss.data.network.UserApi
import com.tillagewireless.ss.others.Constants.DEVICE_TYPE
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val api: UserApi,
    private val userDao: UserDao,
    private val deviceDao: DeviceDao,
    private val userPreferences: UserPreferences
) : BaseRepository(api) {

    suspend fun getUser() = safeApiCall {
        api.getUser()
    }

    suspend fun getDevices() = safeApiCall {
        api.getDevices()
    }

    suspend fun registerDevice(deviceId:String, deviceName:String, vehicleNumber:String) = safeApiCall {
        api.registerDevice(deviceType = DEVICE_TYPE, deviceId=deviceId, deviceName=deviceName, vehicleNumber = vehicleNumber)
    }

    suspend fun registerDeviceVerify(deviceId:String,otp:Int,) = safeApiCall {
        api.registerDeviceVerify(deviceType = DEVICE_TYPE,deviceId=deviceId,otp = otp)
    }

    suspend fun  saveLastSavedPacketId(pid: Long) = userPreferences.savePacketId(pid)

    suspend fun setDefaultDevice(deviceId: String) = userPreferences.saveDefaultDeviceId(deviceId)

    suspend fun saveUser(user:User)= userDao.upsertUser(user)

    suspend fun updateDevices(devices:List<Device>) = deviceDao.upsertDevices(devices)

    suspend fun saveDevice(device:Device) = deviceDao.upsertDevice(device)

    suspend fun saveDevices(devices:List<Device>) = deviceDao.upsertDevices(devices)

    fun getDeviceDetailsLive(deviceId:String) = deviceDao.getDeviceDetailsLive(deviceId)

    fun getDevicesId() = deviceDao.getDevicesId(DEVICE_TYPE)

    fun getDevicesName() = deviceDao.getDevicesName(DEVICE_TYPE)
}