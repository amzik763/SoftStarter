package com.tillagewireless.ss.data.network.responses

data class LoginResponse(
    val access_token: String?,
    val refresh_token: String?,
)