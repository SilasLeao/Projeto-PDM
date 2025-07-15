package com.example.acompanhapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.acompanhapp.ui.theme.AcompanhAppTheme

@Composable
fun HomeScreen(navController: NavController) {
    Column(modifier = Modifier.padding(16.dp)) {
        Image(
            painter = painterResource(id = R.drawable.doctor_image),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
        )

        Text("Bem vindo(a)", style = MaterialTheme.typography.headlineSmall)

        OutlinedTextField(
            value = "",
            onValueChange = {},
            placeholder = { Text("Buscar...") },
            trailingIcon = {
                Icon(Icons.Default.Search, contentDescription = "Buscar")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text("Cadastre ou gerencie equipes médicas, pacientes e familiares.")

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.navigate("dashboard") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Pacientes")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { navController.navigate("equipe") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Equipe Médica")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { /* TODO */ }, modifier = Modifier.fillMaxWidth()) {
            Text("Familiares")
        }
    }
}
