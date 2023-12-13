package com.tillagewireless.ss.ui.home

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.*
import com.tillagewireless.R
import com.tillagewireless.databinding.ActivityHomeBinding
import com.tillagewireless.ss.MainActivity
import com.tillagewireless.ss.configuration
import com.tillagewireless.ss.data.UserPreferences
import com.tillagewireless.ss.data.network.Resource
import com.tillagewireless.ss.ui.auth.AuthActivity
import com.tillagewireless.ss.ui.handleApiError
import com.tillagewireless.ss.ui.home.device.DeviceViewModel
import com.tillagewireless.ss.ui.home.user.UserViewModel
import com.tillagewireless.ss.ui.startNewActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : AppCompatActivity(){

    @Inject
    lateinit var userPreferences: UserPreferences
    lateinit var binding: ActivityHomeBinding
    lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private val viewModel by viewModels<UserViewModel>()
    private val viewModelDevice by viewModels<DeviceViewModel>()
    private lateinit var timer: CountDownTimer
    private var syncTimer = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        userPreferences.getDefaultDeviceId.asLiveData().observe(this) {
            if (it != null) {
                configuration.deviceId = it
            }
        }

        viewModelDevice.updateTopicDataResponse.observe(this, Observer {
            when (it) {
                is Resource.Success -> {
                    Log.d(TAG, "Response Success Synchronizing last update topic from server")
                    viewModelDevice.saveUpdateTopicRecord(it.value)
                    viewModelDevice.clearUpdateTopicDataResponse()
                }
                is Resource.Failure -> handleApiError(it)
                else -> {}
            }
        })

        viewModel.activity.observe(this, Observer {
            Log.d(TAG, "Fragment activity request: $it")
            if (it == "MAIN") {
                viewModel.startNewActivity("NONE")
                startNewActivity(MainActivity::class.java)
            }
        })
        timer = object : CountDownTimer(600000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                syncTimer++;
                if(syncTimer >= SYNC_TIME_LIMIT){
                    syncTimer = 0;
                    viewModelDevice.getUpdateTopicData()
                    Log.d(TAG, "Synchronizing update topic from server")
                }
            }
            override fun onFinish() {
                timer.start()
            }
        }
        timer.start()

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.findNavController()

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.alertsFragment,
                R.id.trackFragment,
                R.id.dashboardFragment,
                R.id.driveScoreFragment
            ),
            binding.drawerLayout
        )
        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.bottomNav.setupWithNavController(navController)
        binding.navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d(TAG, "selected item ${item}")
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {

        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun performLogout() = lifecycleScope.launch {
        val token = userPreferences.refreshToken.first()
        if (token != null) {
            viewModel.logout(token)
        }
        userPreferences.clearAccessToken()
        startNewActivity(AuthActivity::class.java)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.v(TAG, "Home activity destroyed")
    }

    override fun onPause() {
        super.onPause()
        timer.cancel()
        Log.v(TAG, "Home activity paused")
    }

    override fun onResume() {
        super.onResume()
        timer.start()
        Log.v(TAG, "Home activity resumed")
    }

    companion object {
        const val TAG = "HomeActivityDevice"
        const val SYNC_TIME_LIMIT = 1
    }
}