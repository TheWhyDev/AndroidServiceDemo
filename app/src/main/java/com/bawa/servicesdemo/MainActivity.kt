package com.bawa.servicesdemo

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val startButtonView = findViewById<Button>(R.id.startButton)
        val stopButtonView = findViewById<Button>(R.id.stopButton)
        val stopForegroundButton = findViewById<Button>(R.id.stopForegroundButton)
        val statusTextView = findViewById<TextView>(R.id.statusTextView)
        val sendDataButton = findViewById<Button>(R.id.sendDataButton)
        val dataTextView = findViewById<TextView>(R.id.dataTextView)
        val dataEditText = findViewById<EditText>(R.id.dataEditText)

        val startForegroundButton = findViewById<Button>(R.id.startForegroundButton)

        startButtonView.setOnClickListener {
            /**
             * for starting a service
             */
            Intent(this, MyService::class.java).also {
                startService(it)
                statusTextView.text = "Service is running.."
            }




        }
        stopButtonView.setOnClickListener {
            /**
             * for stopping a service
             */
            Intent(this, MyService::class.java).also {

               // it.putExtra("data", STOP_SERVICE)

                stopService(it)
                statusTextView.text = "Service is stopped!"
            }
        }

        sendDataButton.setOnClickListener {
            /**
             * for sending a service
             */
            Intent(this, MyService::class.java).also {
                val data = dataEditText.text.toString()
                it.putExtra("data", data)
                startService(it)

                statusTextView.text = "Service is running.."
            }
        }


        startForegroundButton.setOnClickListener {

            /**
             * if version is OREO or greater, then we call startForegroundService,
             * else we call startService
             */
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(Intent(this, MyForegroundService::class.java))
            } else {
                startService(Intent(this, MyForegroundService::class.java))
            }
        }

        stopForegroundButton.setOnClickListener {
            stopService(Intent(this,MyForegroundService::class.java))
        }

    }
}