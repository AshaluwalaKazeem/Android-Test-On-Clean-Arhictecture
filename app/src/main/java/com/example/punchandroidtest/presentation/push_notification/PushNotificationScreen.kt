package com.example.punchandroidtest.presentation.push_notification

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CloudUpload
import androidx.compose.material.icons.rounded.Description
import androidx.compose.material.icons.rounded.Title
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.punchandroidtest.R
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.punchandroidtest.presentation.push_notification.components.OutlinedEditText
import com.example.punchandroidtest.presentation.ui.theme.ColorPrimaryDark
import com.example.punchandroidtest.presentation.ui.theme.Yellow500
import timber.log.Timber

@Composable
fun PushNotificationScreen(
    viewModel: PushNotificationViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val context = LocalContext.current

    var imageUri by rememberSaveable {
        mutableStateOf<Uri?>(null)
    }
    var selectedImageName by rememberSaveable {
        mutableStateOf<String?>(null)
    }

    val launcher = rememberLauncherForActivityResult(contract =
    ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
        if(imageUri != null){
            selectedImageName = viewModel.queryFileName(imageUri!!, context)
            if(viewModel.queryFileSize(uri!!, context) > 1600000) {
                imageUri = null
                Toast.makeText(context, "Image size too big, You can only select image less than or equals to 1.5mb", Toast.LENGTH_LONG).show()
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (state.isLoading) {
            Spacer(modifier = Modifier.height(20.dp))
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
        if (state.error.isNotBlank()) {
            Text(
                text = state.error,
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.TopCenter)
            )
        }
        if(state.response.messageId != null) {
            Text(
                text = "Push notification sent to all apps. Message Id = ${state.response.messageId}",
                color = ColorPrimaryDark,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            )
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Image(
                    painter = painterResource(id = R.drawable.firebase_icon),
                    contentDescription = "App logo",
                    modifier = Modifier
                        .padding(vertical = 30.dp)
                        .size(90.dp)
                )
                Text(
                    "Send push notification to other apps",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h4,
                )

                Spacer(modifier = Modifier.height(20.dp))

                val focus = LocalFocusManager.current
                var title by rememberSaveable {
                    mutableStateOf("")
                }
                var body by rememberSaveable {
                    mutableStateOf("")
                }

                OutlinedEditText(
                    value = title,
                    onTextChanged = {
                        title = it
                    },
                    placeHolder = "Enter Notification Title",
                    label = "Notification Title",
                    leadingIcon = Icons.Rounded.Title,
                    iconContentDescription = "notification title Icon",
                    focus = focus,
                    maxInput = 50
                )

                Spacer(modifier = Modifier.height(15.dp))

                OutlinedEditText(
                    value = body,
                    onTextChanged = {
                        body = it
                    },
                    placeHolder = "Enter notification body",
                    label = "Notification Body",
                    leadingIcon = Icons.Rounded.Description,
                    iconContentDescription = "notification body Icon",
                    focus = focus
                )
                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        launcher.launch("image/*")
                    },
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(backgroundColor = Yellow500, contentColor = Color.White)
                ) {
                    Icon(imageVector = Icons.Rounded.CloudUpload, contentDescription = "Send Push Notification icon", modifier = Modifier.padding(start = 10.dp))
                    Text(text = "Choose image", modifier = Modifier.padding(vertical = 5.dp, horizontal = 10.dp))
                }

                if(imageUri != null) Text(
                    text = "Selected file: $selectedImageName",
                    style = MaterialTheme.typography.caption
                )
                Spacer(modifier = Modifier.height(15.dp))
                Button(
                    onClick = {
                        if(title.isEmpty() || body.isEmpty() || imageUri == null) {
                            Toast.makeText(context, "Please make sure to input all fields and select a valid image file", Toast.LENGTH_LONG).show()
                            return@Button
                        }
                        viewModel.sendPushNotification(title, body, imageUri!!)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 40.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = ColorPrimaryDark, contentColor = Color.White)
                ) {
                    Text(text = "Send", modifier = Modifier.padding(vertical = 5.dp))
                }
            }
        }
    }
}