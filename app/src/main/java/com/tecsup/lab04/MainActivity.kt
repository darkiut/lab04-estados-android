package com.tecsup.lab04

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tecsup.lab04.ui.theme.Lab04Theme

/**
 * Laboratorio 04 - Estados en Android
 * Alumno: Jose Carlos Vitorino Condori
 * Fecha: 10/11/2025
 * Parte 0: Git - Rama feature-colors
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
                    ColorfulScreen()
                }
            }
        }
    }
}

@Composable
fun ColorfulScreen() {
    var count by remember { mutableStateOf(0) }
    var name by remember { mutableStateOf("") }
    val buttonColor = if (count >= 0) Color(0xFF4CAF50) else Color(0xFFF44336)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Lab 04 - Estados en Android",
            fontSize = 24.sp,
            color = Color(0xFF1976D2)
        )

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Jose Carlos Vitorino Condori",
            fontSize = 16.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(32.dp))

        // TextField
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Ingresa tu nombre") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFE3F2FD),
                unfocusedContainerColor = Color(0xFFF5F5F5)
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (name.isNotEmpty()) {
            Text(
                text = "Hola, $name!",
                fontSize = 20.sp,
                color = Color(0xFF4CAF50)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Contador con color dinámico
        Card(
            modifier = Modifier.padding(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = buttonColor.copy(alpha = 0.1f)
            )
        ) {
            Text(
                text = "Contador: $count",
                fontSize = 32.sp,
                color = buttonColor,
                modifier = Modifier.padding(16.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = { count-- },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF44336)
                )
            ) {
                Text("-")
            }
            Button(
                onClick = { count = 0 },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF9E9E9E)
                )
            ) {
                Text("Reset")
            }
            Button(
                onClick = { count++ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4CAF50)
                )
            ) {
                Text("+")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ColorfulPreview() {
    Lab04Theme {
        ColorfulScreen()
    }
}
