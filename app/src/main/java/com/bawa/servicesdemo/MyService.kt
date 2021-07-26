package com.bawa.servicesdemo

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log


class MyService : Service() {

    val TAG = "MyService"

    init {
        Log.d(TAG, "Service is running")
    }

    /**
     * binds with some component like activity/fragment
     */
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }


    /**
     * this is called when service starts
     */
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val data = intent?.getStringExtra("data")
        data?.let {
            Log.d(TAG, data)
        }
        return START_STICKY
    }

    /**
     * service can be killed by calling selfStop or stopService from any  android component
     */
    override fun onDestroy() {
        stopSelf()
        super.onDestroy()
    }

}