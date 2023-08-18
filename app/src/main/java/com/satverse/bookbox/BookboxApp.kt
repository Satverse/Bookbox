package com.satverse.bookbox

import android.app.Application
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.ExperimentalComposeUiApi
import cat.ereza.customactivityoncrash.config.CaocConfig
import coil.annotation.ExperimentalCoilApi
import dagger.hilt.android.HiltAndroidApp


@ExperimentalCoilApi
@ExperimentalComposeUiApi
@ExperimentalMaterial3Api
@ExperimentalMaterialApi
@HiltAndroidApp
class BookboxApp : Application() {
    override fun onCreate() {
        super.onCreate()
        CaocConfig.Builder.create().restartActivity(MainActivity::class.java).apply()
    }
}