package com.example.acompanhapp.viewmodel.state

import com.example.acompanhapp.model.Paciente

data class DashboardUiState(
    val nomeUsuario: String? = null,
    val idUsuario: String? = null,
    val pacientes: List<Paciente> = emptyList(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null
)
