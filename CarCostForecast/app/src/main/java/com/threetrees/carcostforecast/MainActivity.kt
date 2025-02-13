package com.threetrees.carcostforecast

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.threetrees.carcostforecast.ui.theme.CarCostForecastTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CarCostForecastTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    CarBuyForm()
                }
            }
        }
    }
}

@Composable
fun CarBuyForm() {
    val carPrice = remember { mutableStateOf("") }
    val taxCost = { mutableStateOf("") }
    val monthlyRepayment = { mutableStateOf("") }
    var monthlyCost = { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Enter car price:", style = MaterialTheme.typography.h6)
        TextField(
            value = carPrice.value,
            onValueChange = { carPrice.value = it },
            label = { Text("Car Price") }
        )


        Text(text = "Enter annual tax cost:", style = MaterialTheme.typography.h6)
        TextField(
            value = taxCost.value,
            onValueChange = { taxCost.value = it },
            label = { Text("Tax Cost") }
        )

        Text(text = "Enter monthly repayment amount:", style = MaterialTheme.typography.h6)
        TextField(
            value = monthlyRepayment.value,
            onValueChange = { monthlyRepayment.value = it },
            label = { Text("Tax Cost") }
        )

//        monthlyCost = carPrice * taxCost * monthlyRepayment

        Button(onClick = { /*ENTER SUM HERE */ }, modifier = Modifier.padding(top = 16.dp)) {
            Text(text = "Submit")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CarBuyFormPreview() {
    CarBuyForm()
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CarCostForecastTheme {
        CarBuyForm()
    }
}