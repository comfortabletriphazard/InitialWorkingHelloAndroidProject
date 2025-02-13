package com.threetrees.linkhelper

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import com.threetrees.linkhelper.ui.theme.LinkHelperTheme

class DodgyLinkDialogActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val link = intent.getStringExtra("dodgy_link") ?: ""

        setContentView(ComposeView(this).apply {
            setContent {
                LinkHelperTheme {
                    LinkWarningDialog(
                        link = link,
                        onConfirm = {
                            // Open the link in the browser
                            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                            startActivity(browserIntent)
                            finish() // Close the dialog
                        },
                        onCancel = {
                            finish() // Close the dialog without doing anything
                        }
                    )
                }
            }
        })
    }
}

@Composable
fun LinkWarningDialog(
    link: String,
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onCancel,
        title = { Text("Warning") },
        text = { Text("The link you clicked may be unsafe: $link. Do you want to proceed?") },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Proceed")
            }
        },
        dismissButton = {
            TextButton(onClick = onCancel) {
                Text("Cancel")
            }
        }
    )
}
