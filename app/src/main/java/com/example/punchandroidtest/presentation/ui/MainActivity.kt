package com.example.punchandroidtest.presentation.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.example.punchandroidtest.presentation.base_screen.BaseScreen
import com.example.punchandroidtest.presentation.fetch_api.FetchApiScreen
import com.example.punchandroidtest.presentation.ui.theme.PunchAndroidTestTheme
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // I will be using Jetpack Compose (Android modern toolkit for building native UI) to design the UI for this app since it simplifies and accelerates UI development on Android and its a new tool supported and embraced by Google.
        setContent {
            PunchAndroidTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    BaseScreen()
                }
            }
        }
    }
}