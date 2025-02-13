package com.threetrees.phishprotectorandroid

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log

class LinkClickReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val url = intent.dataString ?: return

        val sharedPrefs = context.getSharedPreferences("URLStorage", Context.MODE_PRIVATE)
        val editor = sharedPrefs.edit()

        val urls = sharedPrefs.getStringSet("clicked_urls", mutableSetOf()) ?: mutableSetOf()
        urls.add(url)

        // Increment Click Counter
        val clickCount = sharedPrefs.getInt("click_count", 0) + 1
        editor.putInt("click_count", clickCount)
        editor.putStringSet("clicked_urls", urls)
        editor.apply()

        Log.d("LinkClickReceiver", "URL Clicked: $url, Total Clicks: $clickCount")
    }
}
