package com.example.punchandroidtest.presentation.push_notification

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CloudUpload
import androidx.compose.material.icons.rounded.Description
import androidx.compose.material.icons.rounded.Send
import androidx.compose.material.icons.rounded.Title
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.punchandroidtest.R
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import com.example.punchandroidtest.presentation.ui.theme.ColorPrimaryDark
import com.example.punchandroidtest.presentation.ui.theme.Yellow500

@Composable
fun PushNotificationScreen(
    viewModel: PushNotificationViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    Box(modifier = Modifier.fillMaxSize()) {
        if (state.isLoading) {
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

                OutlinedTextField(
                    value = title,
                    onValueChange = {
                        if(it.length < 50) title = it
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    placeholder = {
                        Text(text = "Enter Notification Title")
                    },
                    label = {
                        Text("Notification Title")
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.Title,
                            contentDescription = "notification title Icon"
                        )
                    },
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focus.clearFocus()
                        }
                    ),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    singleLine = true,
                    shape = MaterialTheme.shapes.large
                )
                Spacer(modifier = Modifier.height(15.dp))
                OutlinedTextField(
                    value = body,
                    onValueChange = {
                        body = it
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    placeholder = {
                        Text(text = "Enter notification body")
                    },
                    label = {
                        Text("Notification body")
                    },
                    leadingIcon = {
                        Icon(imageVector = Icons.Rounded.Description, contentDescription = "notification body Icon")
                    },
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focus.clearFocus()
                        }
                    ),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    singleLine = true,
                    shape = MaterialTheme.shapes.large,
                )
                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {

                    },
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(backgroundColor = Yellow500, contentColor = Color.White)
                ) {
                    Icon(imageVector = Icons.Rounded.CloudUpload, contentDescription = "Send Push Notification icon", modifier = Modifier.padding(start = 10.dp))
                    Text(text = "Choose image", modifier = Modifier.padding(vertical = 5.dp, horizontal = 10.dp))
                }
                Spacer(modifier = Modifier.height(15.dp))
                Button(
                    onClick = {
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 40.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = ColorPrimaryDark, contentColor = Color.White)
                ) {
                    Text(text = "Send", modifier = Modifier.padding(vertical = 5.dp))
                    Icon(imageVector = Icons.Rounded.Send, contentDescription = "Send Push Notification icon", modifier = Modifier.padding(start = 10.dp))
                }
            }
        }
    }
}