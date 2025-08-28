package com.example.acompanhapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.acompanhapp.viewmodel.EquipeMedicaViewModel
import com.example.acompanhapp.viewmodel.EquipeMedicaViewModelFactory

@Composable
fun EquipeMedicaScreen(
    viewModel: EquipeMedicaViewModel = viewModel(factory = EquipeMedicaViewModelFactory(LocalContext.current))
) {
    val uiState = viewModel.uiState.collectAsState().value
    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        viewModel.loadMedicos()
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        Text("Equipe Médica", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(16.dp))

        if (uiState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            if (uiState.medicos.isEmpty()) {
                Text("Nenhum médico cadastrado.", modifier = Modifier.align(Alignment.CenterHorizontally))
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
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp
                                ),
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
            uiState.errorMessage?.let { Text(it, color = Color.Red) }
        }
    }
}
