package com.tecsup.lab04

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tecsup.lab04.ui.theme.Lab04Theme

/**
 * Laboratorio 04 - Estados en Android
 * Alumno: Jose Carlos Vitorino Condori
 * Fecha: 10/11/2025
 * Partes 1, 2 y 3: MovieCounter App
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
                    MovieCounterApp()
                }
            }
        }
    }
}

/**
 * Pantalla principal de MovieCounter
 * Jose Carlos Vitorino Condori
 */
@Composable
fun MovieCounterApp() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "MovieCounter",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1976D2)
        )

        Text(
            text = "Jose Carlos Vitorino Condori",
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(48.dp))

        // Parte 1: Contador estático
        MovieCounterPart1()

        Spacer(modifier = Modifier.height(32.dp))
        Divider()
        Spacer(modifier = Modifier.height(32.dp))

        // Parte 2: Contador con estado (remember)
        MovieCounterPart2()

        Spacer(modifier = Modifier.height(32.dp))
        Divider()
        Spacer(modifier = Modifier.height(32.dp))

        // Parte 3: Contador con persistencia (rememberSaveable)
        MovieCounterPart3()
    }
}

/**
 * PARTE 1: Contador estático
 * Jose Carlos Vitorino Condori
 */
@Composable
fun MovieCounterPart1(modifier: Modifier = Modifier) {
    val count = 0 // Estático

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFE3F2FD)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Parte 1: Contador Estático",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "You have added $count movies.",
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { /* No hace nada */ }) {
                Text("Add Movie")
            }
        }
    }
}

/**
 * PARTE 2: Contador con estado (remember)
 * Jose Carlos Vitorino Condori
 */
@Composable
fun MovieCounterPart2(modifier: Modifier = Modifier) {
    var count by remember { mutableStateOf(0) }
    var movieName by remember { mutableStateOf("") }

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF3E5F5)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Parte 2: Con remember",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "You have added $count movies.",
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = movieName,
                onValueChange = { movieName = it },
                label = { Text("Movie Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (movieName.isNotBlank()) {
                        count++
                        movieName = ""
                    }
                }
            ) {
                Text("Add Movie")
            }

            if (count > 0) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "⚠️ Al rotar la pantalla, se pierde el estado",
                    fontSize = 12.sp,
                    color = Color(0xFFF44336)
                )
            }
        }
    }
}

/**
 * PARTE 3: Contador con persistencia (rememberSaveable)
 * Jose Carlos Vitorino Condori
 */
@Composable
fun MovieCounterPart3(modifier: Modifier = Modifier) {
    var count by rememberSaveable { mutableStateOf(0) }
    var movieName by rememberSaveable { mutableStateOf("") }

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFE8F5E9)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Parte 3: Con rememberSaveable",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "You have added $count movies.",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4CAF50)
            )
            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = movieName,
                onValueChange = { movieName = it },
                label = { Text("Movie Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (movieName.isNotBlank()) {
                        count++
                        movieName = ""
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4CAF50)
                )
            ) {
                Text("Add Movie")
            }

            if (count > 0) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "✓ El estado persiste al rotar la pantalla",
                    fontSize = 12.sp,
                    color = Color(0xFF4CAF50),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMovieCounter() {
    Lab04Theme {
        MovieCounterApp()
    }
}
