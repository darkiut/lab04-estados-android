package com.tecsup.lab04

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tecsup.lab04.ui.theme.Lab04Theme

/**
 * Laboratorio 04 - Estados en Android
 * Alumno: Jose Carlos Vitorino Condori
 * Fecha: 10/11/2025
 * Parte 0: Git - Segundo Commit - Agregar contador
 */

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab04Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CounterScreen()
                }
            }
        }
    }
}

@Composable
fun CounterScreen() {
    var count by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Lab 04 - Estados en Android",
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Jose Carlos Vitorino Condori",
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Contador: $count",
            fontSize = 32.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(onClick = { count-- }) {
                Text("-")
            }
            Button(onClick = { count = 0 }) {
                Text("Reset")
            }
            Button(onClick = { count++ }) {
                Text("+")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CounterPreview() {
    Lab04Theme {
        CounterScreen()
    }
}
