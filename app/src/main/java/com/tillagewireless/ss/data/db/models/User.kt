package com.tillagewireless.ss.data.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    tableName = "user_info"
)
data class User (
    @PrimaryKey(autoGenerate = true)
    val user_id: Int,
    val mobile: String,
    var is_active: Boolean,
    var email: String? = null,
    val first_name: String,
    val last_name: String?,
    var dob:Date?,
    var address_1:String?,
    var address_2:String?,
    var city:String?,
    var state:String?,
    var pin:String?,
)