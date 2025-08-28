package com.example.acompanhapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.acompanhapp.viewmodel.DashboardViewModel
import com.example.acompanhapp.viewmodel.state.DashboardUiState
import org.koin.androidx.compose.koinViewModel

@Composable
fun DashboardFamiliarScreen(
    viewModel: DashboardViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        viewModel.loadPacientes()
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        Text(
            text = "Olá ${uiState.nomeUsuario ?: "Familiar"}",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(top = 16.dp)
        )

        Divider(modifier = Modifier.padding(vertical = 12.dp))

        if (uiState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            if (uiState.pacientes.isEmpty()) {
                Text("Nenhum paciente encontrado.", modifier = Modifier.align(Alignment.CenterHorizontally))
            } else {
                uiState.pacientes.forEach { paciente ->
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
            uiState.errorMessage?.let { Text(it, color = Color.Red) }
        }
    }
}



// Composable que exibe um card para Condição, Batimentos e Pressão.
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


// Botão usado na tela do dashboard para Exames, Visitas e Medicamentos.
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
