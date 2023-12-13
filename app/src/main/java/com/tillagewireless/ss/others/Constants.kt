package com.tillagewireless.ss.others

object Constants {
    const val RUNNING_DATABASE_NAME = "device_db.db"
    const val FCM_BASE_URL = "https://fcm.googleapis.com"
    const val FCM_SERVER_KEY = "AAAAS0IlYqU:APA91bHMiyjvkNEGFYNquFVzk0z1VLbB5jRqYkZ0-mmTO_dUFhsHEq1DNxrvO1FCN-qJONVivFU931UZnQnR3bKJuRtmqVd4gDv76Nah3K8d-wKqICTKlKF9Fjc-yrJfFbDEWCFq9CYz"
    const val FCM_CONTENT_TYPE = "application/json"
    const val CHANNEL_ID = "vc_channel"
    const val CHANNEL_NAME = "com.vsecureuinfocom.ss.data.fcm"
    const val FCM_TOKEN_ID = "fcmToken"
    const val VSU_BASE_URL = "http://13.235.215.173:5000"
    const val DEFAULT_LOCATION_LAT = 25.2138
    const val DEFAULT_LOCATION_LON = 75.8648
    const val INVALID_LAT:Double = 0.0
    const val INVALID_LON:Double = 0.0
    const val SERVER_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.S"
    const val DEVICE_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss"
    const val DEMO_DEVICE_ID = "868715034820393"//"888888888888888" // 868715034892194
    const val HISTORY_DATA_DAYS: Long = 604800000
    const val DEVICE_TYPE = "SKSS"
    const val MOTOR_STOP =0
    const val MOTOR_STOPING =1
    const val MOTOR_RUN =2
    const val MOTOR_STARTING =3
    const val MOTOR_FAULT = 4
    const val MOTOR_STATUS_NOT_AVILABLE = 0
    // MQTT client configuration //
    const val MQTT_USER_NAME = "mosquitto"
    const val MQTT_PASSWORD = "mosquitto"
    const val MQTT_BROKER = "tcp://54.65.98.165:1883"
    // Motor Commands
    const val RESET_DEVICE_REQ = "RESET_DEVICE_REQ"
    const val MOTOR_START_REQ = "MOTOR_START_REQ"
    const val MOTOR_KICK_START_REQ = "MOTOR_KICK_START_REQ"
    const val MOTOR_STOP_REQ = "MOTOR_STOP_REQ"
    const val FAULT_CLEAR_REQ = "FAULT_CLEAR_REQ"
    const val ALLOWCOUNT_RESET_REQ = "ALLOWCOUNT_RESET_REQ"
    const val START_LOCK_REQ = "START_LOCK_REQ"
    const val START_UNLOCK_REQ = "START_UNLOCK_REQ"
    const val AUTO_MODE_REQ	= "AUTO_MODE_REQ"
    const val MANUAL_MODE_REQ = "MANUAL_MODE_REQ"
    const val SET_BYPAS_DRYRUN_REQ = "SET_BYPAS_DRYRUN_REQ"
    const val CLR_BYPAS_DRYRUN_REQ = "CLR_BYPAS_DRYRUN_REQ"
    const val SET_AUTO_STOP_REQ	= "SET_AUTO_STOP_REQ"
    const val CLR_AUTO_STOP_REQ	= "CLR_AUTO_STOP_REQ"
    const val READ_DEVICE_CONF_REQ = "READ_DEVICE_CONF_REQ"
    const val WRITE_DEVICE_CONF_REQ	= "WRITE_DEVICE_CONF_REQ"
    const val GET_AUTO_STOP_TIMER_REQ = "GET_AUTO_STOP_TIMER_REQ"
    const val START_PERIODIC_UPDATE_REQ = "START_PERIODIC_UPDATE_REQ"
    const val STOP_PERIODIC_UPDATE_REQ = "START_PERIODIC_UPDATE_REQ"

    /* motor faults */
    const val OVER_VOLTAGE	    = 0
    const val UNDER_VOLTAGE	    = 1
    const val MOTOR_SC_FAULT    = 2
    const val MOTOR_OL_FAULT	= 3
    const val DRY_RUN_FAULT		= 4
    const val ROTOR_LOCKED		= 5
    const val POWER_LOSS		= 6
    const val PHASE_REVERSAL	= 7
    const val FREQ_FAULT		= 8
    const val PHASE_LOSS_R		= 9
    const val PHASE_LOSS_Y		= 10
    const val PHASE_LOSS_B		= 11
    const val UNCONF_FAULT		= 12
    const val NO_FIRE   		= 13
    const val WATER_OVERHEAT	= 14
    const val MOT_UNUSED_6		= 15
}