package com.tillagewireless.ss.ui.home.device

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tillagewireless.ss.data.db.models.UpdateTopicItem
import dagger.hilt.android.lifecycle.HiltViewModel
import com.tillagewireless.ss.data.network.Resource
import com.tillagewireless.ss.data.network.responses.RequestResponse
import com.tillagewireless.ss.data.repository.DeviceRepository
import com.tillagewireless.ss.data.network.responses.models.AlertTopicData
import com.tillagewireless.ss.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeviceViewModel @Inject constructor(
    private val repository: DeviceRepository,
) : BaseViewModel(repository) {

    private val _updateTopicDataApiResponse: MutableLiveData<Resource<UpdateTopicItem>> = MutableLiveData()
    val updateTopicDataResponse: LiveData<Resource<UpdateTopicItem>>
        get() = _updateTopicDataApiResponse
    fun clearUpdateTopicDataResponse(){
        _updateTopicDataApiResponse.value = Resource.Noaction
    }

    private val _alertDataApiResponse: MutableLiveData<Resource<AlertTopicData>> = MutableLiveData()
    val alertDataApiResponse: LiveData<Resource<AlertTopicData>>
        get() = _alertDataApiResponse
    fun clearAlertResponse(){
        _alertDataApiResponse.value = Resource.Noaction
    }

    private val _requestResponse: MutableLiveData<Resource<RequestResponse>> = MutableLiveData()
    val requestResponse: LiveData<Resource<RequestResponse>>
        get() = _requestResponse
    fun clearRequestResponse(){
        _requestResponse.value = Resource.Noaction
    }
    fun sendCommandToMotor(
        cmd: String,
        subCmd:String,
        cmdData:Int
    ) = viewModelScope.launch {
        _requestResponse.value = Resource.Loading
        _requestResponse.value = repository.sendCommandToMotor(cmd,subCmd,cmdData)

    }
    fun getUpdateTopicData() = viewModelScope.launch {
        _updateTopicDataApiResponse.value = Resource.Loading
        _updateTopicDataApiResponse.value = repository.getUpdateTopicDataApi()
    }
    fun getAlertTopicData() = viewModelScope.launch {
        repository.getAlertTopicDataApi()
    }
    fun saveUpdateTopicRecord(updateTopicItem: UpdateTopicItem) = viewModelScope.launch {
        repository.saveUpdateTopicRecord(updateTopicItem)
    }
}