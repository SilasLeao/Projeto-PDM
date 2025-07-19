package com.example.acompanhapp

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.acompanhapp.ui.theme.AcompanhAppTheme
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import com.example.acompanhapp.api.RetrofitClient
import com.example.acompanhapp.config.UserPreferences
import com.example.acompanhapp.model.PacienteResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.acompanhapp.model.Paciente

@Composable
fun DashboardFamiliarScreen(navController: NavController) {
    val context = LocalContext.current
    val userPreferences = remember { UserPreferences(context) }

    // Estados para armazenar informações do Usuário e Paciente
    var nomeUsuario by remember { mutableStateOf<String?>(null) }
    var idUsuario by remember { mutableStateOf<String?>(null) }
    var pacientesFiltrados by remember { mutableStateOf<List<Paciente>>(emptyList()) }

    // Recuperar informações do Usuário e Paciente
    LaunchedEffect(Unit) {
        nomeUsuario = userPreferences.getName()
        idUsuario = userPreferences.getId()

        idUsuario?.let { id ->
            RetrofitClient.getClient().getPacientes().enqueue(object : Callback<PacienteResponse> {
                override fun onResponse(call: Call<PacienteResponse>, response: Response<PacienteResponse>) {
                    if (response.isSuccessful) {
                        val todosPacientes = response.body()?.data ?: emptyList()
                        val filtrados = todosPacientes.filter { it.familiaresId?.contains(id) == true }
                        pacientesFiltrados = filtrados

                    }
                }

                override fun onFailure(call: Call<PacienteResponse>, t: Throwable) {
                    Log.e("Dashboard", "Erro ao buscar pacientes: ${t.message}")
                }
            })
        }
    }


    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {

        Text(
            text = "Olá " + (nomeUsuario ?: "Familiar"),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(top = 16.dp)
        )

        Divider(modifier = Modifier.padding(vertical = 12.dp))

        pacientesFiltrados.forEach { paciente ->

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFFE2EBE6))
                    .padding(12.dp)
            ) {
                Text(
                    text = "Paciente: ${paciente.nome ?: "Carregando..."}",
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    CardInfo(
                        title = "Condição:",
                        value = paciente.status ?: "Carregando...",
                        modifier = Modifier.weight(1f)
                    )
                    CardInfo(title = "Batimentos:", value = "100 bpm", modifier = Modifier.weight(1f))
                    CardInfo(title = "Pressão:", value = "180/90", modifier = Modifier.weight(1f))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFFE2EBE6))
                    .padding(12.dp)
            ) {
                Text("Observações", fontWeight = FontWeight.Bold)
                Text(
                    paciente.observacoes ?: "Carregando observações...",
                    fontStyle = FontStyle.Italic
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                ActionButton("Exames", Modifier.weight(1f))
                ActionButton("Visitas", Modifier.weight(1f))
                ActionButton("Medicamentos", Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFFE2EBE6))
                    .padding(12.dp)
            ) {
                Text(
                    "Hospital Central\nAv. Brasil, 1234 - Centro\nTel: (11) 1234-5678"
                )
            }

            Divider(modifier = Modifier.padding(vertical = 32.dp))
        }
    }


}

@Composable
fun CardInfo(title: String, value: String = "", modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.padding(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(title, style = MaterialTheme.typography.bodyLarge)
            if (value.isNotEmpty()) Text(value)
        }
    }
}


@Composable
fun ActionButton(label: String, modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    Button(
        onClick = onClick,
        modifier = modifier.size(80.dp),
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF369A74))
    ) {
        Text(label, color = Color.White)
    }
}
