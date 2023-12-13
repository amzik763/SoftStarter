package com.tillagewireless.ss.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tillagewireless.ss.data.db.models.Device

@Dao
interface DeviceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertDevices(devices: List<Device>):List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertDevice(devices: Device):Long

    /* Get all registered devices */
    @Query("SELECT device_id FROM device_info WHERE device_type= :DeviceType")
    fun getDevicesId(DeviceType:String): LiveData<List<String>>

    @Query("SELECT device_name FROM device_info WHERE device_type= :DeviceType")
    fun getDevicesName(DeviceType:String): LiveData<List<String>>

    @Query("SELECT * FROM device_info WHERE device_id= :device_id")
    fun getDeviceDetailsLive(device_id:String): LiveData<List<Device>>
}