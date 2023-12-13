package com.tillagewireless.ss.data.repository

import kotlinx.coroutines.flow.first
import com.tillagewireless.ss.data.UserPreferences
import com.tillagewireless.ss.data.network.AuthApi
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val api: AuthApi,
    private val preferences: UserPreferences
) : BaseRepository(api) {

    suspend fun login(
        mobileNumber: String,
        password: String
    ) = safeApiCall {

        api.login(mobileNumber, password,preferences.fcmToken.first())
    }

    suspend fun register(
        firstName: String,
        lastName: String,
        mobile: String,
        dob: String
    )= safeApiCall{
        api.register(firstName,lastName,mobile,dob)
    }

    suspend fun setPassword(
        mobile: String,
        password: String,
        otp: Int
    )= safeApiCall{
        api.setPassword(mobile,password,otp)
    }

    suspend fun forgetPassword(mobile: String)= safeApiCall{ api.forgetPassword(mobile) }

    suspend fun sendFcmToken(token: String) = safeApiCall {
        api.sendFcmToken(token)
    }

    suspend fun getAccessToken(): String?{
        return preferences.accessToken.first()
    }

    suspend fun saveFcmToken(token: String) {
        preferences.saveFcmToken(token)
    }

    suspend fun saveAccessTokens(accessToken: String, refreshToken: String) {
        preferences.saveAccessTokens(accessToken, refreshToken)
    }
}