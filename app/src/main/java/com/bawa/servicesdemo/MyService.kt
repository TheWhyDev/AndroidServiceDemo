package com.bawa.servicesdemo

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.*
import android.util.Log


//is a context in itself
class MyService : Service() {

    val TAG = "MyService"
    private var serviceLooper: Looper? = null
    private var serviceHandler: ServiceHandler? = null

    lateinit var  mediaPlayer : MediaPlayer

    init {
        Log.d(TAG, "Service is running")
    }


    private inner class ServiceHandler(looper: Looper) : Handler(looper) {
        override fun handleMessage(msg: Message) {
            //we'll be doing long running task here..

            if( !mediaPlayer.isPlaying){
                mediaPlayer.start()
            }


            for (value in 0..1000) {
                Thread.sleep(2000)
                print("Value : $value --")
            }


            stopSelf(msg.arg1)
        }
    }



    override fun onCreate() {
        super.onCreate()

        mediaPlayer = MediaPlayer.create(this, R.raw.song_mine)


        //Handler  Thread extends a Thread
        HandlerThread("ServiceStartArguments", Process.THREAD_PRIORITY_BACKGROUND).apply {
            //starting the thread
            start()                                                                              //is equivalent to this.start()
            //get the looper from the thread
            serviceLooper = looper
            serviceLooper?.let {
                serviceHandler = ServiceHandler(it)
            }

        }

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

        serviceHandler?.obtainMessage()?.also { msg ->
            msg.arg1 = startId


            //sending a message to start a job along with start id in the
            // above line so that we can differentiate between the jobs and stop them based on msgId
            serviceHandler?.sendMessage(msg)
        }

        return START_STICKY
    }


    /**
     * Stop a service
     * stopSelf()
     * stopService()
     */

    /**
     * service can be killed by calling selfStop or stopService from any  android component
     */
    override fun onDestroy() {
        //stopSelf()

        //stop the media
        if(mediaPlayer.isPlaying){
            mediaPlayer.stop()
        }
        //quit after processing messages currently in the queue
        serviceLooper?.quitSafely()
        super.onDestroy()
    }


}