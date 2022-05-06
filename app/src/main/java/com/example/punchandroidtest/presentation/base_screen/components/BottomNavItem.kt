package com.example.punchandroidtest.presentation.base_screen.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Campaign
import androidx.compose.material.icons.rounded.CloudDownload
import androidx.compose.material.icons.rounded.EventNote
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(var title:String, var icon: ImageVector, var screen_route:String){

    object FetchApi : BottomNavItem("Fetch Api", Icons.Rounded.CloudDownload,"fetch_api")
    object NoteSaved: BottomNavItem("Note Saved", Icons.Rounded.EventNote,"note_saved")
    object Push: BottomNavItem("Push", Icons.Rounded.Campaign,"push_notification")
}