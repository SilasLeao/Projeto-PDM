package com.example.acompanhapp.viewmodel.state

import com.example.acompanhapp.model.Medico
import com.example.acompanhapp.model.User

data class HomeUiState(
    val searchQuery: String = "",
    val medicos: List<Medico> = emptyList(),
    val usuarios: List<User> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
