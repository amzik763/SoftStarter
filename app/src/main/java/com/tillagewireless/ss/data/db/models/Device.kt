package com.tillagewireless.ss.data.db.models

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "device_info", indices = [Index(value = ["device_id"], unique = true)]
)
data class Device (
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var device_type:String,
    var device_id:String,
    var device_name:String?,
    var vehicle_number:String?,
    var is_active: Boolean?,
    var is_connected: Boolean?,
    var is_configured: Boolean?,
    var firmware_ver:String?,
    var config_id: Int?,
)