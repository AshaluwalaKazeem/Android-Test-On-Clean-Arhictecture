package com.example.punchandroidtest

import android.app.Application
import android.util.Log
import com.example.punchandroidtest.common.timber.ReleaseTree
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import timber.log.Timber.Forest.plant


@HiltAndroidApp
class MarsApplication : Application(){

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            plant(Timber.DebugTree())
        } else {
            plant(ReleaseTree())
        }
    }
}
