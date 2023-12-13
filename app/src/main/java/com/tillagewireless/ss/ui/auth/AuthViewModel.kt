package com.tillagewireless.ss.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import com.tillagewireless.ss.data.network.Resource
import com.tillagewireless.ss.data.network.responses.ForgetPasswordResponse
import com.tillagewireless.ss.data.repository.AuthRepository
import com.tillagewireless.ss.data.network.responses.SetPasswordResponse
import com.tillagewireless.ss.data.network.responses.LoginResponse
import com.tillagewireless.ss.data.network.responses.RegisterResponse
import com.tillagewireless.ss.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : BaseViewModel(repository) {

    private val _loginResponse: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
    val loginResponse: LiveData<Resource<LoginResponse>>
        get() = _loginResponse
    fun clearLoginResponse(){
        _loginResponse.value = Resource.Noaction
    }

    private val _registerResponse: MutableLiveData<Resource<RegisterResponse>> = MutableLiveData()
    val registerResponse: LiveData<Resource<RegisterResponse>>
        get() = _registerResponse
    fun clearRegisterResponse(){
        _registerResponse.value = Resource.Noaction
    }

    private val _setPasswordResponse: MutableLiveData<Resource<SetPasswordResponse>> = MutableLiveData()
    val setPasswordResponse: LiveData<Resource<SetPasswordResponse>>
        get() = _setPasswordResponse
    fun clearSetPasswordResponse(){
        _setPasswordResponse.value = Resource.Noaction
    }

    private val _forgetPasswordResponse: MutableLiveData<Resource<ForgetPasswordResponse>> = MutableLiveData()
    val forgetPasswordResponse: LiveData<Resource<ForgetPasswordResponse>>
        get() = _forgetPasswordResponse
    fun clearForgetPasswordResponse(){
        _forgetPasswordResponse.value = Resource.Noaction
    }

    private val _mobile: MutableLiveData<String> = MutableLiveData()
    val mobile: LiveData<String>
        get() = _mobile

    fun login(
        mobileNumber: String,
        password: String
    ) = viewModelScope.launch {
        _loginResponse.value = Resource.Loading
        _loginResponse.value = repository.login(mobileNumber, password)
    }

    fun register(
        firstName: String,
        lastName: String,
        mobile: String,
        dob: String
    ) = viewModelScope.launch {
        _mobile.value = mobile
        _registerResponse.value = Resource.Loading
        _registerResponse.value = repository.register(firstName,lastName,mobile,dob)

    }

    fun setPassword(
        password: String,
        otp:Int
    ) = viewModelScope.launch {
        _setPasswordResponse.value = Resource.Loading
        _setPasswordResponse.value = repository.setPassword(mobile.value!!,password,otp)
    }

    fun forgetPassword(
        mobile: String
    )= viewModelScope.launch {
        _mobile.value = mobile
        _forgetPasswordResponse.value = Resource.Loading
        _forgetPasswordResponse.value = repository.forgetPassword(mobile)
    }

    suspend fun saveAccessTokens(accessToken: String, refreshToken: String) {
        repository.saveAccessTokens(accessToken, refreshToken)
    }
}