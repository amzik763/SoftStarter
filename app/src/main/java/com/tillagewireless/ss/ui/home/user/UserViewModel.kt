package com.tillagewireless.ss.ui.home.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tillagewireless.ss.configuration
import com.tillagewireless.ss.data.db.models.Device
import com.tillagewireless.ss.data.db.models.User
import com.tillagewireless.ss.data.network.Resource
import com.tillagewireless.ss.data.network.responses.*
import com.tillagewireless.ss.data.repository.UserRepository
import com.tillagewireless.ss.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: UserRepository
) : BaseViewModel(repository) {

    private val _user: MutableLiveData<Resource<UserResponse>> = MutableLiveData()
    val user: LiveData<Resource<UserResponse>>
        get() = _user

    private val _devices: MutableLiveData<Resource<DeviceResponse>> = MutableLiveData()
    val devices: LiveData<Resource<DeviceResponse>>
        get() = _devices
    fun clearGetDevicesResponse(){
        _devices.value = Resource.Noaction
    }

    private val _registerDeviceResponse: MutableLiveData<Resource<RegisterVehicleResponse>> = MutableLiveData()
    val registerDeviceResponse: LiveData<Resource<RegisterVehicleResponse>>
        get() = _registerDeviceResponse
    fun clearRegisterDeviceResponse(){
        _registerDeviceResponse.value = Resource.Noaction
    }

    private val _registerDeviceVerifyResponse: MutableLiveData<Resource<Device>> = MutableLiveData()
    val registerDeviceVerifyResponse: LiveData<Resource<Device>>
        get() = _registerDeviceVerifyResponse
    fun clearRegisterDeviceVerifyResponse(){
        _registerDeviceVerifyResponse.value = Resource.Noaction
    }

    private val _registeringDeviceId: MutableLiveData<String> = MutableLiveData()
    val registeringDeviceId: LiveData<String>
        get() = _registeringDeviceId

    private val _registeringIsDefault: MutableLiveData<Boolean> = MutableLiveData()
    val registeringIsDefault: LiveData<Boolean>
        get() = _registeringIsDefault

    fun getUser() = viewModelScope.launch {
        _user.value = Resource.Loading
        _user.value = repository.getUser()
    }

    fun getDevices() = viewModelScope.launch {
        _devices.value = Resource.Loading
        _devices.value = repository.getDevices()
    }

    fun registerDevice(deviceId:String, deviceName:String, vehicleNumber:String, isSetDefault:Boolean) = viewModelScope.launch {
        _registeringDeviceId.value = deviceId
        _registeringIsDefault.value = isSetDefault
        _registerDeviceResponse.value = Resource.Loading
        _registerDeviceResponse.value = repository.registerDevice(deviceId,deviceName,vehicleNumber)
    }

    fun registerDeviceVerify(otp:Int, deviceId:String) = viewModelScope.launch {
        _registerDeviceVerifyResponse.value = Resource.Loading
        _registerDeviceVerifyResponse.value = repository.registerDeviceVerify(deviceId,otp)
    }

    suspend fun  saveLastSavedPacketId(pid: Long) = repository.saveLastSavedPacketId(pid)

    suspend fun setDefaultDevice(deviceId: String) = repository.setDefaultDevice(deviceId)

    suspend fun saveUser(user: User) = repository.saveUser(user)

    suspend fun saveDevice(device:Device) = repository.saveDevice(device)

    suspend fun saveDevices(devices:List<Device>) = repository.saveDevices(devices)

    fun getDevicesId() = repository.getDevicesId()

    fun getDevicesName() = repository.getDevicesName()

    fun getDeviceDetailsLive()= repository.getDeviceDetailsLive(configuration.deviceId)

    private val _mapRefresh = MutableLiveData<Boolean>(false)
    val mapRefresh: LiveData<Boolean> = _mapRefresh

    fun mapRefresh(input: Boolean) {
           _mapRefresh.value =input
    }

    private val _replay = MutableLiveData<Boolean>(false)
    val replay: LiveData<Boolean> = _replay

    fun replyLastTrip(input: Boolean){
        _replay.value = input
    }

    private val _activity = MutableLiveData<String>("NONE")
    val activity: MutableLiveData<String> = _activity

    fun startNewActivity(input: String){
        _activity.value = input
    }

}