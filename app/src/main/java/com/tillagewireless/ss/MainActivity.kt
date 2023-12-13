package com.tillagewireless.ss

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.tillagewireless.R
import com.tillagewireless.ss.data.UserPreferences
import com.tillagewireless.ss.others.Configuration
import com.tillagewireless.ss.ui.auth.AuthActivity
import com.tillagewireless.ss.ui.home.HomeActivity
import com.tillagewireless.ss.ui.startNewActivity
import dagger.hilt.android.AndroidEntryPoint


var configuration = Configuration()
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val userPreferences = UserPreferences(this)

        userPreferences.getDefaultDeviceId.asLiveData().observe(this, Observer {
            if(it != null){
                configuration.deviceId = it
            }
        })
        userPreferences.accessToken.asLiveData().observe(this, Observer {
            val activity = if (it == null) AuthActivity::class.java else HomeActivity::class.java
            Log.d("TimingTest","MainActivity finished" )
            startNewActivity(activity)
        })
    }
    companion object{
        const val TAG = "Websocket_Okhttp"
    }
}