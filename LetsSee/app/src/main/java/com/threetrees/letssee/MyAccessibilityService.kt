package com.threetrees.letssee

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.accessibility.AccessibilityEvent

class MyAccessibilityService : AccessibilityService() {

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        event?.source?.let { node ->
            val text = node.text?.toString()
            if (!text.isNullOrBlank() && (text.startsWith("http") || text.contains("www."))) {
                Log.d("LinkDetected", "URL Clicked: $text")
                // Do something with the intercepted link
            }
        }
    }

    override fun onInterrupt() {
        Log.w("MyAccessibilityService", "Service Interrupted")
    }
}
