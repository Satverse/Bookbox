package com.satverse.bookbox

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import coil.annotation.ExperimentalCoilApi
import com.satverse.bookbox.others.NetworkObserver
import com.satverse.bookbox.ui.screens.main.MainScreen
import com.satverse.bookbox.ui.screens.settings.viewmodels.SettingsViewModel
import com.satverse.bookbox.ui.theme.BookboxTheme
import com.satverse.bookbox.utils.PreferenceUtil
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalMaterialApi
@ExperimentalCoilApi
@ExperimentalMaterial3Api
@ExperimentalComposeUiApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var networkObserver: NetworkObserver
    lateinit var settingsViewModel: SettingsViewModel
    private lateinit var mainViewModel: MainViewModel

    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        PreferenceUtil.initialize(this)
        networkObserver = NetworkObserver(applicationContext)
        settingsViewModel = ViewModelProvider(this)[SettingsViewModel::class.java]
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        // Install splash screen before setting content.
        installSplashScreen().setKeepOnScreenCondition {
            mainViewModel.isLoading.value
        }

        setContent {
            BookboxTheme {
                val status by networkObserver.observe().collectAsState(
                    initial = NetworkObserver.Status.Unavailable
                )

                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val startDestination by mainViewModel.startDestination
                    MainScreen(
                        startDestination = startDestination,
                        networkStatus = status,
                        settingsViewModel = settingsViewModel
                    )
                }
            }
        }
        checkStoragePermission()
    }

    fun checkStoragePermission(): Boolean {
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.d("MainActivity::Storage", "Permission is granted"); true
            } else {
                Log.d("MainActivity::Storage", "Permission is revoked")
                ActivityCompat.requestPermissions(
                    this, arrayOf(WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE), 1
                ); false
            }
        } else {
            true
        }
    }
}