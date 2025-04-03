package com.threetrees.heromail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EmailApp()
        }
    }
}

sealed class Screen {
    object EmailList : Screen()
    object AddEmail : Screen()
    data class EmailDetail(val email: Email) : Screen()
}

@Composable
fun EmailApp() {
    var emails by remember { mutableStateOf(sampleEmails) }
    var currentScreen by remember { mutableStateOf<Screen>(Screen.EmailList) }

    MyApplicationTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            when (val screen = currentScreen) {
                is Screen.EmailList -> EmailListScreen(
                    emails,
                    onEmailClick = { currentScreen = Screen.EmailDetail(it) },
                    onAddEmailClick = { currentScreen = Screen.AddEmail },
                    onDeleteEmail = { emailToDelete -> emails = emails - emailToDelete }
                )

                is Screen.EmailDetail -> EmailDetailScreen(
                    email = screen.email,
                    onBack = { currentScreen = Screen.EmailList })

                is Screen.AddEmail -> AddEmailScreen(onAddEmail = {
                    emails = emails + it
                    currentScreen = Screen.EmailList
                }, onBack = { currentScreen = Screen.EmailList })
            }
        }
    }
}

@Composable
fun EmailListScreen(
    emails: List<Email>,
    onEmailClick: (Email) -> Unit,
    onAddEmailClick: () -> Unit,
    onDeleteEmail: (Email) -> Unit
) {
    var emailToDelete by remember { mutableStateOf<Email?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 56.dp)) {
            items(emails) { email ->
                EmailItem(email, onEmailClick, onLongPress = { emailToDelete = email })
            }
        }
        FloatingActionButton(
            onClick = onAddEmailClick,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomEnd)
        ) {
            Text("+")
        }
    }

    if (emailToDelete != null) {
        AlertDialog(
            onDismissRequest = { emailToDelete = null },
            title = { Text("Delete Email") },
            text = { Text("Are you sure you want to delete this email?") },
            confirmButton = {
                Button(onClick = {
                    emailToDelete?.let { onDeleteEmail(it) }
                    emailToDelete = null
                }) {
                    Text("Delete")
                }
            },
            dismissButton = {
                Button(onClick = { emailToDelete = null }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EmailItem(email: Email, onEmailClick: (Email) -> Unit, onLongPress: (Email) -> Unit) {
    var showDialog by remember { mutableStateOf(false) }

    // Keywords and symbols to check for
    val suspiciousKeywords = listOf("win", "won", "$", "£", "€", "₹")

    // Function to check if email contains suspicious content
    fun containsSuspiciousContent(text: String): Boolean {
        return suspiciousKeywords.any { text.contains(it, ignoreCase = true) }
    }

    // Show the dialog if suspicious content is found
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Suspicious Content Detected") },
            text = { Text("This email may contain suspicious content. Are you sure you want to open it?") },
            confirmButton = {
                Button(onClick = {
                    showDialog = false
                    onEmailClick(email) // Proceed to open email if user confirms
                }) {
                    Text("Open")
                }
            },
            dismissButton = {
                Button(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .combinedClickable(
                onClick = {
                    // Check for suspicious content in email preview and subject
                    if (containsSuspiciousContent(email.subject) || containsSuspiciousContent(email.preview)) {
                        showDialog = true // Show the warning dialog
                    } else {
                        onEmailClick(email) // Proceed to open email if no suspicious content
                    }
                },
                onLongClick = { onLongPress(email) }
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = email.sender, style = MaterialTheme.typography.titleMedium)
            Text(text = email.subject, style = MaterialTheme.typography.bodyMedium)
            Text(text = email.preview, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
fun EmailDetailScreen(email: Email, onBack: () -> Unit) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Button(onClick = onBack) { Text("Back") }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = email.sender, style = MaterialTheme.typography.titleLarge)
        Text(text = email.subject, style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = email.body, style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun AddEmailScreen(onAddEmail: (Email) -> Unit, onBack: () -> Unit) {
    var sender by remember { mutableStateOf("") }
    var subject by remember { mutableStateOf("") }
    var preview by remember { mutableStateOf("") }
    var body by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        OutlinedTextField(
            value = sender,
            onValueChange = { sender = it },
            label = { Text("Sender") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = subject,
            onValueChange = { subject = it },
            label = { Text("Subject") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = preview,
            onValueChange = { preview = it },
            label = { Text("Preview") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = body,
            onValueChange = { body = it },
            label = { Text("Body") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
            Button(onClick = onBack) { Text("Cancel") }
            Button(onClick = {
                if (sender.isNotBlank() && subject.isNotBlank() && preview.isNotBlank() && body.isNotBlank()) {
                    onAddEmail(Email(sender, subject, preview, body))
                }
            }) { Text("Add") }
        }
    }
}

data class Email(val sender: String, val subject: String, val preview: String, val body: String)

val sampleEmails = listOf(
    Email(
        "Alice",
        "Meeting Reminder",
        "Don't forget our meeting at 3 PM.",
        "Hey, just reminding you about our meeting today at 3 PM. See you there!"
    ),
    Email(
        "Bob",
        "Weekend Plans",
        "Want to go hiking this weekend?",
        "Hey, I was thinking of going hiking this weekend. Are you interested?"
    ),
    Email(
        "Charlie",
        "Project Update",
        "The project deadline has been extended.",
        "Good news! The project deadline has been extended by one week."
    ),
    Email(
        "Win Big Now!",
        "You've won a $1,000 gift card!",
        "Claim your reward before it expires!",
        "Congratulations! You have been randomly selected to receive a $1,000 gift card. Click the link below to claim your prize. Hurry, this offer expires soon!"
    )
)

@Preview
@Composable
fun PreviewEmailApp() {
    EmailApp()
}