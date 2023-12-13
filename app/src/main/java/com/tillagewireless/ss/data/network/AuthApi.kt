package com.tillagewireless.ss.data.network

import com.tillagewireless.ss.data.network.responses.ForgetPasswordResponse
import com.tillagewireless.ss.data.network.responses.SetPasswordResponse
import com.tillagewireless.ss.data.network.responses.LoginResponse
import com.tillagewireless.ss.data.network.responses.RegisterResponse
import okhttp3.ResponseBody
import retrofit2.http.*

interface AuthApi : BaseApi {

    @FormUrlEncoded
    @POST("/v1/auth/login")
    suspend fun login(
        @Field("mobile") mobileNumber: String,
        @Field("password") password: String,
        @Field("fcm_token") fcmToken: String?
    ): LoginResponse

    @FormUrlEncoded
    @POST("/v1/auth/register")
    suspend fun register(
        @Field("first_name") firstName: String,
        @Field("last_name") lastName: String,
        @Field("mobile") mobile: String,
        @Field("dob") dob: String
    ): RegisterResponse

    @FormUrlEncoded
    @POST("/v1/auth/set_password")
    suspend fun setPassword(
        @Field("mobile") mobile: String,
        @Field("password") password: String,
        @Field("otp") otp: Int
    ): SetPasswordResponse

    @FormUrlEncoded
    @POST("/v1/auth/forget_password")
    suspend fun forgetPassword(
        @Field("mobile") mobile: String,
    ): ForgetPasswordResponse

    @FormUrlEncoded
    @POST("/save_fcm_token")
    suspend fun sendFcmToken(
        @Field("fcm_token") token: String,
    ): ResponseBody

}

