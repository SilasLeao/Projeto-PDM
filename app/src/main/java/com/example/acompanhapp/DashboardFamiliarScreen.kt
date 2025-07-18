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

@Composable
fun DashboardFamiliarScreen(navController: NavController) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Familiar 2", style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(12.dp))

        Text("Paciente", style = MaterialTheme.typography.titleSmall)

        Row(modifier = Modifier.fillMaxWidth()) {
            CardInfo(title = "Condição", modifier = Modifier.weight(1f))
            CardInfo(title = "Batimentos", value = "100 bpm", modifier = Modifier.weight(1f))
            CardInfo(title = "Pressão", value = "180/90", modifier = Modifier.weight(1f))
        }


        Spacer(modifier = Modifier.height(16.dp))

        Text("Observações", style = MaterialTheme.typography.titleSmall)
        Text("Paciente em estado delicado. Monitoramento contínuo.", fontStyle = FontStyle.Italic)

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

        Text("Hospital Central\nAv. Brasil, 1234 - Centro\nTel: (11) 1234-5678")
    }
}

@Composable
fun CardInfo(title: String, value: String = "", modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .padding(4.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(title, style = MaterialTheme.typography.bodyLarge)
            if (value.isNotEmpty()) Text(value)
        }
    }
}

@Composable
fun ActionButton(label: String, modifier: Modifier = Modifier) {
    Button(onClick = { /* TODO */ }, modifier = modifier) {
        Text(label)
    }
}
