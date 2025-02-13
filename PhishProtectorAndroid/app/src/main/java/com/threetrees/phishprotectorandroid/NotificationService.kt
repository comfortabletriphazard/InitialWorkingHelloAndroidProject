package com.threetrees.phishprotectorandroid

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import java.util.Timer
import java.util.TimerTask

class NotificationService : Service() {

    private lateinit var timer: Timer
    private lateinit var timerTask: TimerTask
    private val TAG = "NotificationService"
    private val INTERVAL_SECS = 5 * 1000  // 5 seconds

    private val handler = Handler(Looper.getMainLooper())

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "Service started")
        startTimer()
        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "Service created")
    }

    override fun onDestroy() {
        Log.d(TAG, "Service destroyed")
        stopTimerTask()
        super.onDestroy()
    }

    private fun startTimer() {
        if (!::timer.isInitialized) {
            timer = Timer()
            initializeTimerTask()
            timer.schedule(timerTask, INTERVAL_SECS.toLong(), INTERVAL_SECS.toLong())
        }
    }

    private fun stopTimerTask() {
        if (::timer.isInitialized) {
            timer.cancel()
        }
        if (::timerTask.isInitialized) {
            timerTask.cancel()
        }
    }

    private fun initializeTimerTask() {
        timerTask = object : TimerTask() {
            override fun run() {
                handler.post {
                    sendNotification()
                }
            }
        }
    }

    private fun sendNotification() {
        Log.d(TAG, "Notification Triggered")
        // Implement your notification logic here
    }
}