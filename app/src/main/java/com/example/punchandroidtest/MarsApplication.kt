package com.example.punchandroidtest

import android.app.Application
import coil.ImageLoader
import com.example.punchandroidtest.common.Constants
import com.example.punchandroidtest.common.ResponseHeaderInterceptor
import com.example.punchandroidtest.common.timber.ReleaseTree
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.HiltAndroidApp
import okhttp3.OkHttpClient
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

        val imageLoader = ImageLoader.Builder(applicationContext)
            .okHttpClient {
                OkHttpClient.Builder()
                    // This header will be added to every image request.
                    .addNetworkInterceptor(ResponseHeaderInterceptor("Cache-Control", "max-age=31536000,public"))
                    .build()
            }
            .build()
    }
}
