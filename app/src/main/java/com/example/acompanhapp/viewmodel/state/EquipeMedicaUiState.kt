package com.example.acompanhapp.viewmodel.state

import com.example.acompanhapp.model.Medico

data class EquipeMedicaUiState(
    val medicos: List<Medico> = emptyList(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null
)