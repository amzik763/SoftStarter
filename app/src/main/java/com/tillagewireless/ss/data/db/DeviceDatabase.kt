package com.tillagewireless.ss.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tillagewireless.ss.data.db.models.Device
import com.tillagewireless.ss.data.db.models.User

@Database(
    entities = [User::class,Device::class],
    version= 4,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class DeviceDatabase : RoomDatabase(){
    abstract fun getDeviceDao():DeviceDao
    abstract fun getUserDao():UserDao
}