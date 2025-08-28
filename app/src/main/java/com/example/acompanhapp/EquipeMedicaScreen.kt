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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.acompanhapp.viewmodel.EquipeMedicaViewModel
import com.example.acompanhapp.viewmodel.EquipeMedicaViewModelFactory


// Composable que exibe a tela da equipe médica, fazendo uma requisição para buscar a lista de médicos cadastrados
@Composable
fun EquipeMedicaScreen(
    navController: NavController,
    viewModel: EquipeMedicaViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = EquipeMedicaViewModelFactory(
        LocalContext.current
    )
    )
) {
    val medicos by viewModel.medicos.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val scrollState = rememberScrollState()

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
                            Text(
                                medico.email,
                                color = Color.White,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

