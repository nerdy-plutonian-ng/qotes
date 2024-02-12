package com.plutoapps.qotes.ui.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.core.app.NotificationCompat
import com.plutoapps.qotes.R

class Notify {

    fun createNotification(title : String,description : String,context: Context) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            createNotificationChannel(context)
        }
        showNotification(context,title,description)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(context: Context) {
        val channelId = "my_channel_id"
        val channelName = "My Notification Channel"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel =  NotificationChannel(channelId, channelName, importance)
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun showNotification(context: Context,title: String,description: String) {
        val notification = NotificationCompat.Builder(context, "my_channel_id")
            .setContentTitle(title)
            .setContentText(description)
            .setSmallIcon(R.drawable.quote)
            .setStyle(NotificationCompat.BigTextStyle())
            .build()

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1, notification)
    }
}