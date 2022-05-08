package com.example.punchandroidtest

import android.R
import android.app.Application
import android.util.Log
import android.widget.Toast
import com.example.punchandroidtest.common.Constants
import com.example.punchandroidtest.common.timber.ReleaseTree
import com.google.firebase.messaging.FirebaseMessaging
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
        FirebaseMessaging.getInstance().subscribeToTopic(Constants.TOPIC)
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Timber.d("Push notification topic subscription failed")
                }
                Timber.d("Successfully subscribed to ${Constants.TOPIC}")
            }
    }
}
