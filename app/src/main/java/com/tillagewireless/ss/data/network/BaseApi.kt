package com.tillagewireless.ss.data.network

import okhttp3.ResponseBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface BaseApi {
    @FormUrlEncoded
    @POST("/v1/auth/logout")
    suspend fun logout(
        @Field("refresh_token") refreshToken: String
    ): ResponseBody
}