package com.tillagewireless.ss.data.network.responses

import com.tillagewireless.ss.data.db.models.Device

class DeviceResponse: ArrayList<Device>()

data class RequestResponse(
    val status: String,
)