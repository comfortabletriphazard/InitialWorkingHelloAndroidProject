package com.threetrees.linkhelper

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.threetrees.linkhelper.ui.theme.LinkHelperTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LinkHelperTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    EnableAccessibilityServiceScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun EnableAccessibilityServiceScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current // Get the context
    Button(
        onClick = {
            val intent = Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS)
            context.startActivity(intent) // Use context to navigate to settings
        },
        modifier = modifier
    ) {
        Text("Enable Accessibility Service")
    }
}