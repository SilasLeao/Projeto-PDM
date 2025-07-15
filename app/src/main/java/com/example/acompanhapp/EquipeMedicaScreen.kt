package com.example.acompanhapp

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun EquipeMedicaScreen(navController: NavController) {
    val membros = listOf(
        "Luiz" to "Médico - Urgência",
        "Silas" to "Médico - Urgência",
        "João Vittor" to "Médico - Urgência",
        "Kaua" to "Cirurgião - Emergência",
        "Lucas" to "Enfermeiro"
    )

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Equipe Médica", style = MaterialTheme.typography.headlineSmall)

        membros.forEach { (nome, cargo) ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF4CAF50))
            ) {
                Row(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(nome, color = Color.White, style = MaterialTheme.typography.bodyLarge)
                        Text(cargo, color = Color.White)
                        Text("email@email", color = Color.White, fontSize = MaterialTheme.typography.bodySmall.fontSize)
                    }

                    Row {
                        IconButton(onClick = { /* Editar */ }) {
                            Icon(Icons.Default.Edit, contentDescription = null, tint = Color.White)
                        }
                        IconButton(onClick = { /* Deletar */ }) {
                            Icon(Icons.Default.Delete, contentDescription = null, tint = Color.Red)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        FloatingActionButton(onClick = { /* Adicionar */ }, modifier = Modifier.align(Alignment.End)) {
            Icon(Icons.Default.Add, contentDescription = "Adicionar")
        }
    }
}
