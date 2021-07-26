package com.bawa.servicesdemo

import android.app.Service
import android.content.Intent
import android.os.IBinder

class ServiceLifeCycle : Service() {

    var startMode = START_STICKY
    var binder :IBinder? = null
    var allowRebind = false

    override fun onCreate() {
       //Service is being created
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        //The service is starting, due to a call to startService
        return startMode
    }

    override fun onBind(p0: Intent?): IBinder? {
        //a client is binding with the service using bindService()
        return binder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        // all clients have unbound with unbindService
        return allowRebind
    }

    override fun onRebind(intent: Intent?) {
        //A client is binding to the service with bindService()
        //after onUnbind() has already been called
    }

    override fun onDestroy() {
        super.onDestroy()
        //service no longer  used and is being destroyed
    }
}