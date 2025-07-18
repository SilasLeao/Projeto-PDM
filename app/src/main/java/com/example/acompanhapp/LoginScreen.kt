package com.example.acompanhapp

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.acompanhapp.api.RetrofitClient
import com.example.acompanhapp.model.UserResponse
import com.example.acompanhapp.ui.theme.AcompanhAppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun LoginScreen(navController: NavController) {
    // Estados para email e senha
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    val context = LocalContext.current

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
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            placeholder = { Text("email@exemplo.com") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = senha,
            onValueChange = { senha = it },
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
            onClick = {
                isLoading = true

                // Chamada assíncrona Retrofit
                RetrofitClient.getClient().getUsers().enqueue(object : retrofit2.Callback<UserResponse> {
                    override fun onResponse(
                        call: retrofit2.Call<UserResponse>,
                        response: retrofit2.Response<UserResponse>
                    ) {
                        isLoading = false
                        if (response.isSuccessful) {
                            val userResponse = response.body()
                            val usuarios = userResponse?.data ?: emptyList()
                            val usuarioEncontrado = usuarios.find {
                                it.email == email && it.password == senha
                            }
                            if (usuarioEncontrado != null) {
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
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Entrar")
        }

        if (isLoading) {
            Spacer(modifier = Modifier.height(16.dp))
            CircularProgressIndicator()
        }

    }
}

