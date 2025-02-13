package com.threetrees.deletethis.android

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.ContentObserver
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.threetrees.deletethis.Greeting
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (permissions.all { it.value }) {
                // Permissions granted, start monitoring
                startMonitoringSettings()
            } else {
                // Permissions denied, handle accordingly
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SettingsMonitorScreen()
                }
            }
        }

        checkAndRequestPermissions()
    }

    private fun checkAndRequestPermissions() {
        val permissions = mutableListOf(
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_SETTINGS
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissions.add(Manifest.permission.POST_NOTIFICATIONS)
        }

        val permissionsToRequest = permissions.filter {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }.toTypedArray()

        if (permissionsToRequest.isNotEmpty()) {
            requestPermissionLauncher.launch(permissionsToRequest)
        } else {
            startMonitoringSettings()
        }
    }

    private fun startMonitoringSettings() {
        val settingsMonitor = SettingsMonitor(this)
        lifecycleScope.launch {
            settingsMonitor.settingsFlow.collect { settings ->
                // Update UI with settings
            }
        }
    }
}

@Composable
fun SettingsMonitorScreen() {
    val context = LocalContext.current
    val settingsMonitor = remember { SettingsMonitor(context) }
    val settings by settingsMonitor.settingsFlow.collectAsState(initial = SettingsData())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SettingsItem(
            title = "Screen Brightness",
            icon = Icons.Filled.CheckCircle,
            value = settings.brightness.toString(),
            onClick = {
                context.startActivity(Intent(Settings.ACTION_DISPLAY_SETTINGS))
            }
        )
        SettingsItem(
            title = "Airplane Mode",
            icon = Icons.Filled.CheckCircle,
            value = if (settings.airplaneMode) "On" else "Off",
            onClick = {
                context.startActivity(Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS))
            }
        )
        SettingsItem(
            title = "Ringer Mode",
            icon = Icons.Filled.CheckCircle,
            value = settings.ringerMode,
            onClick = {
                context.startActivity(Intent(Settings.ACTION_SOUND_SETTINGS))
            }
        )
    }
}

@Composable
fun SettingsItem(title: String, icon: ImageVector, value: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = icon, contentDescription = title, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = title, style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(1f))
            Text(text = value, style = MaterialTheme.typography.bodyLarge)
        }
    }
}

data class SettingsData(
    val brightness: Int = 0,
    val airplaneMode: Boolean = false,
    val ringerMode: String = "Normal"
)

class SettingsMonitor(private val context: Context) {

    private val _settingsFlow = MutableStateFlow(SettingsData())
    val settingsFlow: StateFlow<SettingsData> = _settingsFlow

    private val contentObserver = object : ContentObserver(Handler(Looper.getMainLooper())) {
        override fun onChange(selfChange: Boolean, uri: Uri?) {
            super.onChange(selfChange, uri)
            updateSettings()
        }
    }

    init {
        registerObservers()
        updateSettings()
    }

    private fun registerObservers() {
        val contentResolver = context.contentResolver
        contentResolver.registerContentObserver(
            Settings.System.getUriFor(Settings.System.SCREEN_BRIGHTNESS),
            true,
            contentObserver
        )
        contentResolver.registerContentObserver(
            Settings.Global.getUriFor(Settings.Global.AIRPLANE_MODE_ON),
            true,
            contentObserver
        )

    }

    private fun updateSettings() {
        val brightness = getScreenBrightness()
        val airplaneMode = isAirplaneModeOn()
        val ringerMode = getRingerMode()
        _settingsFlow.value = SettingsData(brightness, airplaneMode, ringerMode)
    }

    private fun getScreenBrightness(): Int {
        return Settings.System.getInt(
            context.contentResolver,
            Settings.System.SCREEN_BRIGHTNESS,
            0
        )
    }

    private fun isAirplaneModeOn(): Boolean {
        return Settings.Global.getInt(
            context.contentResolver,
            Settings.Global.AIRPLANE_MODE_ON,
            0
        ) != 0
    }

    private fun getRingerMode(): String {
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as android.media.AudioManager
        return when (audioManager.ringerMode) {
            android.media.AudioManager.RINGER_MODE_NORMAL -> "Normal"
            android.media.AudioManager.RINGER_MODE_SILENT -> "Silent"
            android.media.AudioManager.RINGER_MODE_VIBRATE -> "Vibrate"
            else -> "Unknown"
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        SettingsMonitorScreen()
    }
}