package com.tillagewireless.ss.data.network

import com.tillagewireless.ss.data.db.models.Device
import com.tillagewireless.ss.data.network.responses.*
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface UserApi : BaseApi {
    @GET("/user")
    suspend fun getUser(): UserResponse

    @GET("/get_devices")
    suspend fun getDevices(): DeviceResponse

    @FormUrlEncoded
    @POST("/register_device")
    suspend fun registerDevice(
        @Field("device_type") deviceType: String,
        @Field("device_id") deviceId: String,
        @Field("device_name") deviceName: String,
        @Field("vehicle_number") vehicleNumber: String,
    ): RegisterVehicleResponse

    @FormUrlEncoded
    @POST("/register_device_verify")
    suspend fun registerDeviceVerify(
        @Field("device_type") deviceType: String,
        @Field("device_id") deviceId: String,
        @Field("otp") otp: Int,
    ): Device

}