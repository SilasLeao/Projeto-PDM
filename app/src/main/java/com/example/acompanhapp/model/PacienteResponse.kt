package com.example.acompanhapp.model

data class PacienteResponse(
    val status: String,
    val message: String,
    val data: List<Paciente>
)