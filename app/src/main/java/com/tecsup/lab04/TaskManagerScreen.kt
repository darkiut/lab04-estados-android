package com.tecsup.lab04

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tecsup.lab04.ui.theme.Lab04Theme

/**
 * Parte 4: Ejercicio Personal - Task Manager
 * Aplicación de gestión de tareas con estados persistentes
 * Alumno: Jose Carlos Vitorino Condori
 * Fecha: 11/11/2025
 */

// Data class para representar una tarea
data class Task(
    val id: Int,
    val title: String,
    var isCompleted: Boolean = false
)

/**
 * Pantalla principal del Task Manager
 * Jose Carlos Vitorino Condori
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskManagerScreen() {
    // Estados persistentes usando rememberSaveable
    var taskList by rememberSaveable { mutableStateOf(listOf<Task>()) }
    var taskInput by rememberSaveable { mutableStateOf("") }
    var nextId by rememberSaveable { mutableStateOf(1) }
    var showDialog by remember { mutableStateOf(false) }

    // Estadísticas
    val totalTasks = taskList.size
    val completedTasks = taskList.count { it.isCompleted }
    val pendingTasks = totalTasks - completedTasks

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            "Task Manager",
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
                    containerColor = Color(0xFF1976D2),
                    titleContentColor = Color.White
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true },
                containerColor = Color(0xFF4CAF50)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Task",
                    tint = Color.White
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Tarjeta de estadísticas
            StatisticsCard(
                total = totalTasks,
                completed = completedTasks,
                pending = pendingTasks
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Lista de tareas
            if (taskList.isEmpty()) {
                EmptyState()
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(taskList, key = { it.id }) { task ->
                        TaskItem(
                            task = task,
                            onToggleComplete = {
                                taskList = taskList.map {
                                    if (it.id == task.id) {
                                        it.copy(isCompleted = !it.isCompleted)
                                    } else {
                                        it
                                    }
                                }
                            },
                            onDelete = {
                                taskList = taskList.filter { it.id != task.id }
                            }
                        )
                    }
                }
            }
        }

        // Dialog para agregar tarea
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Nueva Tarea") },
                text = {
                    TextField(
                        value = taskInput,
                        onValueChange = { taskInput = it },
                        label = { Text("Título de la tarea") },
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                confirmButton = {
                    Button(
                        onClick = {
                            if (taskInput.isNotBlank()) {
                                taskList = taskList + Task(nextId, taskInput)
                                nextId++
                                taskInput = ""
                                showDialog = false
                            }
                        }
                    ) {
                        Text("Agregar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}

/**
 * Tarjeta de estadísticas
 * Jose Carlos Vitorino Condori
 */
@Composable
fun StatisticsCard(total: Int, completed: Int, pending: Int) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFE3F2FD)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            StatItem(label = "Total", value = total, color = Color(0xFF2196F3))
            StatItem(label = "Completadas", value = completed, color = Color(0xFF4CAF50))
            StatItem(label = "Pendientes", value = pending, color = Color(0xFFFF9800))
        }
    }
}

/**
 * Item de estadística individual
 * Jose Carlos Vitorino Condori
 */
@Composable
fun StatItem(label: String, value: Int, color: Color) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .background(color.copy(alpha = 0.2f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = value.toString(),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            fontSize = 12.sp,
            color = Color.Gray
        )
    }
}

/**
 * Item de tarea individual
 * Jose Carlos Vitorino Condori
 */
@Composable
fun TaskItem(
    task: Task,
    onToggleComplete: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggleComplete() },
        colors = CardDefaults.cardColors(
            containerColor = if (task.isCompleted) {
                Color(0xFFE8F5E9)
            } else {
                Color.White
            }
        ),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = task.isCompleted,
                onCheckedChange = { onToggleComplete() },
                colors = CheckboxDefaults.colors(
                    checkedColor = Color(0xFF4CAF50)
                )
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = task.title,
                fontSize = 16.sp,
                modifier = Modifier.weight(1f),
                textDecoration = if (task.isCompleted) {
                    TextDecoration.LineThrough
                } else {
                    TextDecoration.None
                },
                color = if (task.isCompleted) {
                    Color.Gray
                } else {
                    Color.Black
                }
            )

            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Task",
                    tint = Color(0xFFF44336)
                )
            }
        }
    }
}

/**
 * Estado vacío cuando no hay tareas
 * Jose Carlos Vitorino Condori
 */
@Composable
fun EmptyState() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "📝",
            fontSize = 64.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "No hay tareas",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Presiona el botón + para agregar una tarea",
            fontSize = 14.sp,
            color = Color.Gray
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTaskManager() {
    Lab04Theme {
        TaskManagerScreen()
    }
}
