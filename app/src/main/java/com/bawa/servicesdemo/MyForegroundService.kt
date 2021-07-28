package com.bawa.servicesdemo

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat

class MyForegroundService : Service() {

    val channelId = "channelId"

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        //create notification channel
        createNotificationChannel()

        val activityIntent = Intent(this, MainActivity::class.java)

        val pendingIntent = PendingIntent.getActivity(this, 0, activityIntent, 0)

        val notification = NotificationCompat.Builder(
            this,
            channelId
        ).setContentTitle("Foreground notification title")
            .setContentText("notification content")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        startForeground(
            1,
            notification
        )

        return START_STICKY
    }


    /**
     * allows us to group the notifications strating android version O or Oreo
     */
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId,
                "Foreground Notification",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            val manager = getSystemService(NotificationManager::class.java)

            manager.createNotificationChannel(notificationChannel)
        }
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        stopForeground(true)
        stopSelf()
        super.onDestroy()
    }


}