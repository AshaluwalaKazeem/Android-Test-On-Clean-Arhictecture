package com.example.punchandroidtest.data.firebase.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.TextUtils
import androidx.core.app.NotificationCompat
import com.example.punchandroidtest.R
import com.example.punchandroidtest.common.Constants
import com.example.punchandroidtest.presentation.ui.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.random.Random

class MarsFirebaseService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        val intent = Intent(this, MainActivity::class.java)
        if(remoteMessage.notification != null) {
            val title = remoteMessage.notification!!.title!!
            val body = remoteMessage.notification!!.body!!
            val url = remoteMessage.data["image"]
            if(!TextUtils.isEmpty(url)) {
                processNotificationWithImage(url!!, title, body, intent)
            }else {
                showNotification(this, title , body, intent, null)
            }
        }else {
            val title = remoteMessage.data["title"] ?: ""
            val body = remoteMessage.data["body"] ?: ""
            val url = remoteMessage.data["image"]

            if(!TextUtils.isEmpty(url)) {
                processNotificationWithImage(url!!, title, body, intent)
            }else
                showNotification(this, title, body, intent, null)
        }
    }

    private fun processNotificationWithImage(url: String, title: String, body: String, intent: Intent?) {
        val job = CoroutineScope(Dispatchers.Main).launch {
            Picasso.get()
                .load(url)
                .into(object : Target {
                    override fun onBitmapLoaded(
                        bitmap: Bitmap?,
                        from: Picasso.LoadedFrom?
                    ) {
                        showNotification(context = this@MarsFirebaseService, title = title, body = body, intent, bitmap = bitmap)
                    }

                    override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {

                    }

                    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {

                    }

                })
        }
    }

    private fun showNotification(context: Context, title: String, body: String, intent: Intent?, bitmap: Bitmap?) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notificationId = Random.nextInt()

        val builder: NotificationCompat.Builder = if (bitmap != null) {
            NotificationCompat.Builder(context, Constants.CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setLargeIcon(bitmap)
                .setContentTitle(title)
                .setContentText(body)
                .setStyle(NotificationCompat.BigPictureStyle()
                    .bigPicture(bitmap))
        }else {
            NotificationCompat.Builder(context, Constants.CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(title)
                .setContentText(body)
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(Constants.CHANNEL_ID, Constants.CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }

        if(intent != null) {
            val stackBuilder = TaskStackBuilder.create(context)
            stackBuilder.addNextIntent(intent)
            val pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
            builder.setContentIntent(pendingIntent)
        }

        notificationManager.notify(notificationId, builder.build())
    }
}