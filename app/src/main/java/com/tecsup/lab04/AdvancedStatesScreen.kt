package com.tecsup.lab04

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
 * Parte 5: Tutorial Avanzado - Estados en Jetpack Compose
 * Conceptos avanzados: State hoisting, derivedStateOf, side effects
 * Alumno: Jose Carlos Vitorino Condori
 * Fecha: 11/11/2025
 */

/**
 * Pantalla principal del tutorial avanzado
 * Jose Carlos Vitorino Condori
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdvancedStatesScreen() {
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            "Estados Avanzados",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            "Jose Carlos Vitorino Condori",
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.7f)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF6200EE),
                    titleContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Tabs para diferentes ejemplos
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = Color(0xFF6200EE),
                contentColor = Color.White
            ) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = { Text("State Hoisting") }
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = { Text("Derived State") }
                )
                Tab(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    text = { Text("Side Effects") }
                )
            }

            // Contenido según tab seleccionada
            when (selectedTab) {
                0 -> StateHoistingExample()
                1 -> DerivedStateExample()
                2 -> SideEffectsExample()
            }
        }
    }
}

/**
 * Ejemplo 1: State Hoisting (Elevar el estado)
 * Jose Carlos Vitorino Condori
 */
@Composable
fun StateHoistingExample() {
    var name by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var showGreeting by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFF3E5F5)
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    "Concepto: State Hoisting",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(
                    "Elevar el estado al componente padre permite compartirlo entre componentes hijos",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Estado compartido entre componentes
        UserInputForm(
            name = name,
            email = email,
            onNameChange = { name = it },
            onEmailChange = { email = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { showGreeting = name.isNotBlank() && email.isNotBlank() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Validar Datos")
        }

        if (showGreeting) {
            Spacer(modifier = Modifier.height(16.dp))
            UserGreeting(name = name, email = email)
        }
    }
}

@Composable
fun UserInputForm(
    name: String,
    email: String,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            TextField(
                value = name,
                onValueChange = onNameChange,
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = email,
                onValueChange = onEmailChange,
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun UserGreeting(name: String, email: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFE8F5E9)
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "✓ Datos Válidos",
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4CAF50)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text("Hola, $name!")
            Text("Email: $email")
        }
    }
}

/**
 * Ejemplo 2: Derived State (Estado derivado)
 * Jose Carlos Vitorino Condori
 */
@Composable
fun DerivedStateExample() {
    var items by rememberSaveable { mutableStateOf(listOf<String>()) }
    var newItem by rememberSaveable { mutableStateOf("") }

    // Estado derivado - se recalcula automáticamente
    val itemCount by remember { derivedStateOf { items.size } }
    val hasItems by remember { derivedStateOf { items.isNotEmpty() } }
    val totalCharacters by remember { derivedStateOf { items.sumOf { it.length } } }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFE1F5FE)
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    "Concepto: Derived State",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(
                    "Estados calculados automáticamente a partir de otros estados",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Estadísticas derivadas
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            StatCard("Items", itemCount.toString(), Color(0xFF2196F3))
            StatCard("Caracteres", totalCharacters.toString(), Color(0xFFFF9800))
            StatCard("Estado", if (hasItems) "Con datos" else "Vacío", Color(0xFF4CAF50))
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Agregar items
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextField(
                value = newItem,
                onValueChange = { newItem = it },
                label = { Text("Nuevo item") },
                modifier = Modifier.weight(1f)
            )
            Button(
                onClick = {
                    if (newItem.isNotBlank()) {
                        items = items + newItem
                        newItem = ""
                    }
                }
            ) {
                Text("+")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Lista de items
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items) { item ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFF5F5F5)
                    )
                ) {
                    Text(
                        text = "$item (${item.length} chars)",
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun StatCard(label: String, value: String, color: Color) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = color.copy(alpha = 0.1f)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                value,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
            Text(label, fontSize = 12.sp)
        }
    }
}

/**
 * Ejemplo 3: Side Effects (Efectos secundarios)
 * Jose Carlos Vitorino Condori
 */
@Composable
fun SideEffectsExample() {
    var counter by rememberSaveable { mutableStateOf(0) }
    var logs by remember { mutableStateOf(listOf<String>()) }

    // LaunchedEffect - ejecuta código suspendible
    LaunchedEffect(counter) {
        if (counter > 0) {
            logs = logs + "LaunchedEffect: Contador cambió a $counter"
        }
    }

    // DisposableEffect - limpia recursos
    DisposableEffect(Unit) {
        logs = logs + "DisposableEffect: Componente iniciado"
        onDispose {
            // Aquí se limpiarían recursos
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFFFF3E0)
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    "Concepto: Side Effects",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(
                    "Efectos secundarios que se ejecutan durante el ciclo de vida del composable",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFE3F2FD)
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Contador: $counter", fontSize = 32.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { counter++ }) {
                    Text("Incrementar")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Log de efectos:", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(logs) { log ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFF5F5F5)
                    )
                ) {
                    Text(
                        log,
                        modifier = Modifier.padding(8.dp),
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAdvancedStates() {
    Lab04Theme {
        AdvancedStatesScreen()
    }
}
