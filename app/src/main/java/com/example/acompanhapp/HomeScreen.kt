package com.example.acompanhapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.acompanhapp.ui.theme.AcompanhAppTheme

@Composable
fun HomeScreen(navController: NavController) {
    Column(modifier = Modifier.padding(0.dp)) {
        Image(
            painter = painterResource(id = R.drawable.doctor_image),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(225.dp)
        )

        Text(
            text = "Bem vindo(a)".uppercase(),
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(top = 32.dp, start = 16.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = "",
            onValueChange = {},
            placeholder = { Text("Buscar...") },
            trailingIcon = {
                Icon(Icons.Default.Search, contentDescription = "Buscar")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text("Cadastre ou gerencie equipes médicas, pacientes e familiares.", modifier = Modifier.padding(start = 16.dp, end = 16.dp))

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            Button(

                onClick = { navController.navigate("equipe") },
                modifier = Modifier
                    .size(140.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF369A74)
            )
            ) {
                Text("Equipe Médica")
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(

                onClick = { navController.navigate("dashboard") },
                modifier = Modifier
                    .size(140.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF369A74)
                )
            ) {
                Text("Familiares")
            }
        }

    }
}
