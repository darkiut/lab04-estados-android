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
 * Parte 0: Git - Tercer Commit - Agregar TextField
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
                    InteractiveScreen()
                }
            }
        }
    }
}

@Composable
fun InteractiveScreen() {
    var count by remember { mutableStateOf(0) }
    var name by remember { mutableStateOf("") }

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

        // TextField
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Ingresa tu nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (name.isNotEmpty()) {
            Text(
                text = "Hola, $name!",
                fontSize = 20.sp
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Contador
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
fun InteractivePreview() {
    Lab04Theme {
        InteractiveScreen()
    }
}
