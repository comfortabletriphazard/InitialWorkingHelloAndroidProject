package com.threetrees.kidsapp.android

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GridLayout(
                        onSquareClick = { activityClass ->
                            val intent = Intent(this, activityClass)
                            startActivity(intent)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun GridLayout(onSquareClick: (Class<*>) -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.weight(1f)) {
            Square(
                color = Color.Red,
                text = stringResource(id = R.string.square_1_text),
                onClick = { onSquareClick(Activity1::class.java) },
                modifier = Modifier.weight(1f)
            )
            Square(
                color = Color.Green,
                text = stringResource(id = R.string.square_2_text),
                onClick = { onSquareClick(Activity2::class.java) },
                modifier = Modifier.weight(1f)
            )
        }
        Row(modifier = Modifier.weight(1f)) {
            Square(
                color = Color.Blue,
                text = stringResource(id = R.string.square_3_text),
                onClick = { onSquareClick(Activity3::class.java) },
                modifier = Modifier.weight(1f)
            )
            Square(
                color = Color.Yellow,
                text = stringResource(id = R.string.square_4_text),
                onClick = { onSquareClick(Activity4::class.java) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun Square(color: Color, text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(4.dp)
            .background(color)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, color = Color.White, style = MaterialTheme.typography.bodyLarge)
    }
}

// Placeholder Activities
class Activity1 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Text(
                stringResource(id = R.string.activity_1_text),
                modifier = Modifier.fillMaxSize(),
                color = Color.Red
            )
        }
    }
}

class Activity2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Text(
                stringResource(id = R.string.activity_2_text),
                modifier = Modifier.fillMaxSize(),
                color = Color.Green
            )
        }
    }
}

class Activity3 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Text(
                stringResource(id = R.string.activity_3_text),
                modifier = Modifier.fillMaxSize(),
                color = Color.Blue
            )
        }
    }
}

class Activity4 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Text(
                stringResource(id = R.string.activity_4_text),
                modifier = Modifier.fillMaxSize(),
                color = Color.Yellow
            )
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        GridLayout(onSquareClick = {})
    }
}