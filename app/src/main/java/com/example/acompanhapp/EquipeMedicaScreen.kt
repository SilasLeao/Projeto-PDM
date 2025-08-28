package com.example.acompanhapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.acompanhapp.model.Medico
import com.example.acompanhapp.viewmodel.EquipeMedicaViewModel
import org.koin.androidx.compose.koinViewModel
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment

@Composable
fun EquipeMedicaScreen(viewModel: EquipeMedicaViewModel = koinViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) { viewModel.loadMedicos() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        Text("Equipe Médica", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(16.dp))

        if (uiState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(alignment = Alignment.CenterHorizontally))
        } else if (uiState.errorMessage != null) {
            Text("Erro: ${uiState.errorMessage}")
        } else if (uiState.medicos.isEmpty()) {
            Text("Nenhum médico cadastrado.")
        } else {
            uiState.medicos.forEach { medico ->
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
