package com.example.acompanhapp

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.acompanhapp.api.RetrofitClient
import com.example.acompanhapp.config.UserPreferences
import com.example.acompanhapp.model.UserResponse
import kotlinx.coroutines.launch
import android.util.Base64
import androidx.compose.runtime.collectAsState
import com.example.acompanhapp.viewmodel.LoginViewModel
import org.mindrot.jbcrypt.BCrypt
import androidx.compose.runtime.LaunchedEffect
import com.example.acompanhapp.viewmodel.LoginViewModelFactory


// Composable que representa a tela de login do aplicativo.
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = LoginViewModelFactory(LocalContext.current)
    )
) {
    val email by viewModel.email.collectAsState()
    val senha by viewModel.senha.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val loginSuccess by viewModel.loginSuccess.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    val context = LocalContext.current

    val coroutineScope = rememberCoroutineScope() // ainda pode usar se quiser

    // Navegação após login
    LaunchedEffect(loginSuccess) {
        if (loginSuccess) navController.navigate("home")
    }

    // Mostrar toast de erro
    LaunchedEffect(errorMessage) {
        errorMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(100.dp))

        Image(
            painter = painterResource(id = R.drawable.medico),
            contentDescription = null,
            modifier = Modifier.size(200.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFF369A74))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Fazer Login",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Email
            OutlinedTextField(
                value = email,
                onValueChange = { viewModel.onEmailChange(it) },
                placeholder = { Text("email@exemplo.com", color = Color(0xFFA1A1A1)) },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .clip(RoundedCornerShape(50.dp))
                    .background(Color(0xFF146144)),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent,
                    cursorColor = Color.White,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedContainerColor = Color(0xFF146144),
                    unfocusedContainerColor = Color(0xFF146144)
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Senha
            OutlinedTextField(
                value = senha,
                onValueChange = { viewModel.onSenhaChange(it) },
                placeholder = { Text("*******", color = Color(0xFFA1A1A1)) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .clip(RoundedCornerShape(50.dp))
                    .background(Color(0xFF146144)),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent,
                    cursorColor = Color.White,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedContainerColor = Color(0xFF146144),
                    unfocusedContainerColor = Color(0xFF146144)
                )
            )

            Text(
                "Esqueci minha senha",
                modifier = Modifier.padding(top = 4.dp),
                fontSize = MaterialTheme.typography.bodySmall.fontSize,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botão de login
            Button(
                onClick = { viewModel.login() },
                modifier = Modifier.fillMaxWidth(0.8f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color(0xFF146144)
                ),
                shape = RoundedCornerShape(50.dp)
            ) {
                Text("Entrar")
            }

            if (isLoading) {
                Spacer(modifier = Modifier.height(16.dp))
                CircularProgressIndicator()
            }
        }
    }
}



