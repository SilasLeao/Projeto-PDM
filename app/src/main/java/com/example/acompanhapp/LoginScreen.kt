package com.example.acompanhapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.acompanhapp.ui.theme.AcompanhAppTheme

@Composable
fun LoginScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.medico),
            contentDescription = null,
            modifier = Modifier.size(120.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Fazer Login", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text("Email") },
            placeholder = { Text("email@exemplo.com") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text("Senha") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            "Esqueci minha senha",
            modifier = Modifier
                .align(Alignment.End)
                .padding(top = 4.dp),
            fontSize = MaterialTheme.typography.bodySmall.fontSize,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.navigate("home") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Entrar")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Divider()

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = { /* TODO */ }) {
            Text("Ainda não possui uma conta? Cadastrar-se")
        }
    }
}

