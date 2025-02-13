package com.threetrees.linkhelper

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Intent
import android.view.accessibility.AccessibilityEvent

class DodgyLinkAccessibilityService : AccessibilityService() {

    override fun onServiceConnected() {
        super.onServiceConnected()
        val info = AccessibilityServiceInfo().apply {
            eventTypes = AccessibilityEvent.TYPE_VIEW_CLICKED or AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED
            feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC
            notificationTimeout = 100
        }
        serviceInfo = info
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event == null || event.packageName == null) return

        val text = event.text.joinToString(separator = " ")
        if (isDodgyLink(text)) {
            launchDodgyLinkDialog(text)
        }
    }

    override fun onInterrupt() {
        // Handle interruptions if needed
    }

    private fun isDodgyLink(text: String): Boolean {
        val dodgyDomains = listOf("example-malware.com", "dodgy-link.net")
        return dodgyDomains.any { text.contains(it) }
    }

    private fun launchDodgyLinkDialog(link: String) {
        val intent = Intent(this, DodgyLinkDialogActivity::class.java).apply {
            putExtra("dodgy_link", link)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) // Required to start activity from service
        }
        startActivity(intent)
    }
}
