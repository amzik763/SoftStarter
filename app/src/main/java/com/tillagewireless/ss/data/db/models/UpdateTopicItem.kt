package com.tillagewireless.ss.data.db.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UpdateTopicItem(
    var id: Long? = null,
    var DeviceId: String,
    var ServTimeStemp: String? = null,
    var TimeStemp: String? = null,
    var A9SwVer: Int? = null,
    var StmSwVer: Int? = null,
    var StmHwVer: Int? = null,
    var Config: Int? = null,
    var Signal: Int? = null,
    var BatVolt: Int? = null,
    var StartLock: Boolean? =null,
    var AutoStart: Boolean? =null,
    var AutoStop: Boolean? =null,
    var AutoStopTim: Int? = null,
    var DrunByps: Boolean? =null,
    var MotorStatus: Int? = null,
    var NumOfStart: Int? = null,
    var MotorFault: Long? = null,
    var DeviceFault: Long? = null,
    var VoltR: Int? = null,
    var VoltY: Int? = null,
    var VoltB: Int? = null,
    var AmpR: Int? = null,
    var AmpY: Int? = null,
    var AmpB: Int? = null,
    var HP: Int? = null,
    var Energy: Int? = null,
    var PF: Int? = null,
    var FreqR: Int? = null,
    var FreqY: Int? = null,
    var FreqB: Int? = null,
    var AngleR: Int? = null,
    var AngleY: Int? = null,
    var AngleB: Int? = null,
    var Temp: Int? = null
)