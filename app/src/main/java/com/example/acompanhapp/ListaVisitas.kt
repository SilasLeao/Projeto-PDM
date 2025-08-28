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
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ListaVisitas(
    pacienteId: String,
    viewModel: DashboardViewModel
) {
    val visitasMap by viewModel.visitasPorPaciente.collectAsState()
    val visitas = visitasMap[pacienteId] ?: emptyList()

    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text("Visitas agendadas:", fontWeight = FontWeight.Bold)
        if (visitas.isEmpty()) {
            Text("Nenhuma visita agendada.", fontStyle = FontStyle.Italic)
        } else {
            visitas.forEach { visita ->
                val formattedDate = try {
                    val date = inputFormat.parse(visita.dataHora)
                    outputFormat.format(date)
                } catch (e: Exception) {
                    visita.dataHora // fallback se ocorrer erro
                }
                Text("- $formattedDate")
            }
        }
    }
}
