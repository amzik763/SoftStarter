package com.tillagewireless.ss.data.network

import com.tillagewireless.ss.data.db.models.UpdateTopicItem
import com.tillagewireless.ss.data.network.responses.RequestResponse
import com.tillagewireless.ss.data.network.responses.UpdateTopicResponse
import com.tillagewireless.ss.data.network.responses.models.AlertTopicData
import com.tillagewireless.ss.others.Constants.DEVICE_TYPE
import retrofit2.http.*

interface DeviceAPI : BaseApi {
    @GET("/$DEVICE_TYPE/update_topic_data")
    suspend fun getAllUpdateTopicData(
        @Query("start_date") dataFrom: String?,
        @Query("device_id") deviceId: String?): UpdateTopicResponse

    @GET("/$DEVICE_TYPE/last_update_topic_data")
    suspend fun getUpdateTopicData(
        @Query("device_id") deviceId: String?): UpdateTopicItem

    @GET("/$DEVICE_TYPE/last_alert_topic_data")
    suspend fun getAlertTopicData(
        @Query("device_id") deviceId: String?): AlertTopicData

    @FormUrlEncoded
    @POST("/$DEVICE_TYPE/topic/Req")
    suspend fun sendCommandToMotor(
        @Field("DeviceId") deviceId: String,
        @Field("cmd") cmd: String,
        @Field("SubCmd") SubCmd: String,
        @Field("CmdData") CmdData: Int,
    ): RequestResponse
}