package com.tillagewireless.ss.data.network.responses

data class TokenResponse(
    val access_token: String?,
    val refresh_token: String?
)