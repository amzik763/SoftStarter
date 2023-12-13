package com.tillagewireless.ss.data.repository

import com.tillagewireless.ss.data.network.BaseApi
import com.tillagewireless.ss.data.network.SafeApiCall

abstract class BaseRepository(private val api: BaseApi) : SafeApiCall {

    suspend fun logout(refreshToken: String) = safeApiCall {
        api.logout(refreshToken)
    }
}