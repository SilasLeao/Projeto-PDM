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


// Composable que exibe a tela da equipe médica, fazendo uma requisição para buscar a lista de médicos cadastrados
@Composable
fun EquipeMedicaScreen(navController: NavController) {
    // Estado para armazenar a lista de médicos retornada pela API
    var medicos by remember { mutableStateOf<List<Medico>>(emptyList()) }
    // Estado para controlar se os dados estão sendo carregados
    var isLoading by remember { mutableStateOf(true) }
    // Contexto para exibir Toasts
    val context = LocalContext.current
    // Estado para rolagem vertical da tela
    val scrollState = rememberScrollState()

    // Efeito executado ao iniciar a composição para carregar dados da API
    LaunchedEffect(Unit) {
        RetrofitClient.getClient().getEquipeMedica().enqueue(object : Callback<EquipeMedicaResponse> {
            // Callback executado quando a resposta da API é recebida
            override fun onResponse(call: Call<EquipeMedicaResponse>, response: Response<EquipeMedicaResponse>) {
                isLoading = false
                if (response.isSuccessful) {
                    // Atualiza a lista de médicos com os dados da resposta
                    medicos = response.body()?.data ?: emptyList()
                } else {
                    // Mostra mensagem de erro em caso de falha na resposta
                    Toast.makeText(context, "Erro na resposta: ${response.code()}", Toast.LENGTH_LONG).show()
                }
            }

            // Callback executado caso a requisição falhe
            override fun onFailure(call: Call<EquipeMedicaResponse>, t: Throwable) {
                isLoading = false
                Toast.makeText(context, "Erro na conexão: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        Text("Equipe Médica", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(16.dp))

        // Se estiver carregando, mostra indicador de progresso circular
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            if (medicos.isEmpty()) {
                Text("Nenhum médico cadastrado.", modifier = Modifier.align(Alignment.CenterHorizontally))
            } else {
                // Para cada médico, cria um Card com as informações dele
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
