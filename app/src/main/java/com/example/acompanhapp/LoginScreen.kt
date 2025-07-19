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

// Composable que representa a tela de login do aplicativo.
@Composable
fun LoginScreen(navController: NavController) {
    // Estados para armazenar email, senha e status de carregamento
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val userPrefs = remember { UserPreferences(context) }

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
            Text("Fazer Login", style = MaterialTheme.typography.headlineSmall, color = Color.White)

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = {
                    Text(
                        "email@exemplo.com",
                        color = Color(0xFFA1A1A1)
                    )
                },
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

            OutlinedTextField(
                value = senha,
                onValueChange = { senha = it },
                placeholder = {
                    Text(
                        "*******",
                        color = Color(0xFFA1A1A1)
                    )
                },
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
                modifier = Modifier
                    .padding(top = 4.dp),
                fontSize = MaterialTheme.typography.bodySmall.fontSize,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botão para tentar efetuar login
            Button(
                onClick = {
                    isLoading = true
                    // Realiza requisição para obter lista de usuários e validar credenciais
                    RetrofitClient.getClient().getUsers().enqueue(object : retrofit2.Callback<UserResponse> {
                        override fun onResponse(
                            call: retrofit2.Call<UserResponse>,
                            response: retrofit2.Response<UserResponse>
                        ) {
                            isLoading = false
                            if (response.isSuccessful) {
                                val usuarios = response.body()?.data ?: emptyList()
                                // Procura usuário com as credenciais usadas no login
                                val usuarioEncontrado = usuarios.find {
                                    it.email == email && it.password == senha
                                }
                                if (usuarioEncontrado != null) {
                                    // Salva dados do usuário na DataStore
                                    coroutineScope.launch {
                                        userPrefs.saveUser(
                                            email = usuarioEncontrado.email,
                                            name = usuarioEncontrado.nome,
                                            id = usuarioEncontrado.id
                                        )
                                    }
                                    // Navega para a tela principal após login bem sucedido
                                    navController.navigate("home")
                                } else {
                                    Toast.makeText(context, "Email ou senha incorretos", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                Toast.makeText(context, "Erro na resposta: ${response.code()}", Toast.LENGTH_LONG).show()
                            }
                        }

                        override fun onFailure(call: retrofit2.Call<UserResponse>, t: Throwable) {
                            isLoading = false
                            Toast.makeText(context, "Erro na conexão: ${t.message}", Toast.LENGTH_LONG).show()
                        }
                    })
                },
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

