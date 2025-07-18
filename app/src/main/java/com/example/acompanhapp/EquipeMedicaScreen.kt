package com.example.acompanhapp

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.acompanhapp.api.RetrofitClient
import com.example.acompanhapp.model.Medico
import com.example.acompanhapp.model.EquipeMedicaResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


@Composable
fun EquipeMedicaScreen(navController: NavController) {
    var medicos by remember { mutableStateOf<List<Medico>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    val context = LocalContext.current

    val scrollState = rememberScrollState() // Scroll state

    LaunchedEffect(Unit) {
        RetrofitClient.getClient().getEquipeMedica().enqueue(object : Callback<EquipeMedicaResponse> {
            override fun onResponse(call: Call<EquipeMedicaResponse>, response: Response<EquipeMedicaResponse>) {
                isLoading = false
                if (response.isSuccessful) {
                    medicos = response.body()?.data ?: emptyList()
                } else {
                    Toast.makeText(context, "Erro na resposta: ${response.code()}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<EquipeMedicaResponse>, t: Throwable) {
                isLoading = false
                Toast.makeText(context, "Erro na conexão: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    // Aplica scroll no conteúdo
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        Text("Equipe Médica", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            if (medicos.isEmpty()) {
                Text("Nenhum médico cadastrado.", modifier = Modifier.align(Alignment.CenterHorizontally))
            } else {
                medicos.forEach { medico ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF0FBA95))
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = medico.nome,
                                color = Color.White,
                                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold, fontSize = 20.sp),
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            Text(medico.cargo, color = Color.White)
                            Text(medico.setor, color = Color.White)
                            Text(medico.email, color = Color.White, style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(top = 8.dp))
                        }
                    }
                }
            }
        }
    }
}
