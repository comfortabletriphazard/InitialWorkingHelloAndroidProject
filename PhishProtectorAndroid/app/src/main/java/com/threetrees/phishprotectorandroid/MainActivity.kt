package com.threetrees.phishprotectorandroid

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.threetrees.phishprotectorandroid.ui.theme.PhishProtectorAndroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createNotificationChannel()
        enableEdgeToEdge()
        val serviceIntent = Intent(this, FloatingTextService::class.java)
        startService(serviceIntent)

        setContent {
            PhishProtectorAndroidTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(Modifier.height(20.dp))

                        // Button for Display over other apps
                        DisplayOverAppsButton()

                        Spacer(Modifier.height(20.dp))

                        // Persistent text switch
                        ToggleSwitch(
                            label = "Persistent Text",
                            toggleSwitchOn = false,
                            onToggleChanged = {}
                        )

                        Spacer(Modifier.height(20.dp))

                        Text(
                            text = "URL's clicked list",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(16.dp)
                        )
                        UrlsClickedList()

                        Spacer(Modifier.height(20.dp))

                        // Close App but Keep Running Button
                        BackgroundCloseButton()
                    }
                }
            }
        }
    }

    @Composable
    fun UrlsClickedList() {
        val context = LocalContext.current
        val sharedPrefs = context.getSharedPreferences("URLStorage", Context.MODE_PRIVATE)

        var urls by remember { mutableStateOf(sharedPrefs.getStringSet("clicked_urls", emptySet())?.toList() ?: emptyList()) }
        var clickCount by remember { mutableStateOf(sharedPrefs.getInt("click_count", 0)) }

        LaunchedEffect(Unit) {
            while (true) {
                urls = sharedPrefs.getStringSet("clicked_urls", emptySet())?.toList() ?: emptyList()
                clickCount = sharedPrefs.getInt("click_count", 0)
                kotlinx.coroutines.delay(1000) // Refresh every second
            }
        }

        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Text("Total Clicks: $clickCount", fontWeight = FontWeight.Bold)

            urls.forEach { url ->
                Text(text = url, modifier = Modifier.padding(4.dp))
            }
        }
    }



    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "overlay_channel",
                "Overlay Service",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }
}

@Composable
fun BackgroundCloseButton() {
    val context = LocalActivity.current as Activity

    Button(
        onClick = {
            context.moveTaskToBack(true) // Moves the app to the background
        },
        modifier = Modifier.padding(16.dp)
    ) {
        Text("Close App (Keep Running)")
    }
}

@Composable
fun DisplayOverAppsButton(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var hasPermission by remember { mutableStateOf(Settings.canDrawOverlays(context)) }

    // Recheck permission when returning to the app
    DisposableEffect(Unit) {
        val checkPermission = { hasPermission = Settings.canDrawOverlays(context) }
        checkPermission() // Initial check
        onDispose { }
    }

    Button(
        onClick = {
            if (!hasPermission) {
                val intent = Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:${context.packageName}")
                )
                context.startActivity(intent)
            }
        },
        modifier = modifier
    ) {
        Text(text = if (hasPermission) "Permission Granted" else "Grant Overlay Permission")
    }
}

@Composable
fun ToggleSwitch(
    label: String,
    toggleSwitchOn: Boolean,
    onToggleChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var isSwitchOn by remember { mutableStateOf(toggleSwitchOn) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(text = label, modifier = Modifier.padding(end = 8.dp))

        Switch(
            checked = isSwitchOn,
            onCheckedChange = {
                isSwitchOn = it
                onToggleChanged(it)

                val serviceIntent = Intent(context, FloatingTextService::class.java)
                if (isSwitchOn) {
                    context.startService(serviceIntent)
                    Log.d("ToggleSwitch", "Starting FloatingTextService")
                } else {
                    context.stopService(serviceIntent)
                    Log.d("ToggleSwitch", "Stopping FloatingTextService")
                }
            },
            thumbContent = if (isSwitchOn) {
                {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = null,
                        modifier = Modifier.size(SwitchDefaults.IconSize),
                    )
                }
            } else {
                null
            }
        )
    }
}