package com.tillagewireless.ss.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.tillagewireless.ss.data.db.models.Point
import java.util.*

class Converters {
    @TypeConverter
    fun latLngToJson(point: List<Point>): String{
        var string: String
        point.let {
            string = Gson().toJson(it)
        }
        return string
    }

    @TypeConverter
    fun jsonToLatLng(string: String): List<Point>{
        var point: MutableList<Point>
        string.let{
            point =  Gson().fromJson(it, Array<Point>::class.java).toList() as MutableList<Point>
        }
        return point
    }

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }
}