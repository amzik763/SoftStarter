package com.tillagewireless.ss.others

import java.util.*

data class Configuration(
    var deviceId : String = Constants.DEMO_DEVICE_ID,
    var lastKnownSyncTimestamp: Long = (Date().time - Constants.HISTORY_DATA_DAYS),
)