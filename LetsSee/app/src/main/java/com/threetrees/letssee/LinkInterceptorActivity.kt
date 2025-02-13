package com.threetrees.letssee

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class LinkInterceptorActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get the intercepted URL safely
        val interceptedUrl = intent?.data?.toString() ?: "Unknown URL"

        setContent {
            ConfirmationScreen(interceptedUrl, ::openBrowserChooser, ::finish)
        }
    }

    private fun openBrowserChooser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(Intent.createChooser(intent, "Choose a browser"))
        finish() // Close this activity after user selects
    }
}

@Composable
fun ConfirmationScreen(url: String, onConfirm: (String) -> Unit, onCancel: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Do you want to open this link?", fontSize = 18.sp)
        Text(url, fontSize = 14.sp, modifier = Modifier.padding(8.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Button(onClick = { onConfirm(url) }) {
                Text("Yes, Select Browser")
            }
            Button(onClick = { onCancel() }) {
                Text("No, Go Back")
            }
        }
    }
}
