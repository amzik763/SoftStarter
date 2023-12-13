package com.tillagewireless.ss.data.fcm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_ONE_SHOT
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.tillagewireless.R
import com.tillagewireless.ss.MainActivity
import com.tillagewireless.ss.data.UserPreferences
import com.tillagewireless.ss.others.Constants.CHANNEL_ID
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FirebaseService : FirebaseMessagingService() {
    @Inject
    lateinit var userPreferences: UserPreferences
    private val applicationScope = CoroutineScope(Dispatchers.Default)

    override fun onNewToken(newToken: String) {
        super.onNewToken(newToken)
        Log.d(TAG, "FCM new token received")
        applicationScope.launch {
            userPreferences.saveFcmToken(newToken)
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        var status = 0;
        super.onMessageReceived(message)
        message.notification?.title?.let { Log.w("FirebaseService", it) }
        message.notification?.body?.let {
            Log.w("FirebaseService", it)
            if(it == "MOTOR STARTING") {
                status = 3
            }else if (it == "MOTOR STOPING")
            {
                status = 1
            }else if(it == "MOTOR STOP"){
                status = 0;
            }else if (it == "MOTOR RUNING"){
                status = 2
            }else if(it == "MOTOR FAULT"){
                status = 4
            }
            applicationScope.launch {
                userPreferences.saveMotorStatus(status)
            }
        }
        val intent = Intent(this, MainActivity::class.java)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationId = 0

        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager)
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this,0,intent,FLAG_ONE_SHOT)
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.skaio_2)
            .setContentTitle(message.notification?.title)
            .setContentText(message.notification?.body)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        notificationManager.notify(notificationId,notification.build())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager: NotificationManager){
        val channelName = "channelName"
        val channel = NotificationChannel(CHANNEL_ID, channelName, IMPORTANCE_HIGH).apply {
            description = "my_channel_description"
            enableLights(true)
            lightColor = Color.RED
        }
        notificationManager.createNotificationChannel(channel)
    }
    companion object{
        const val TAG = "FirebaseService"
    }
}