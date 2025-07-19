package com.example.acompanhapp.model

data class Paciente(
    val id: String,
    val hospitalId: String,
    val nome: String,
    val exames: List<String>,
    val familiaresId: List<String>,
    val status: String,
    val observacoes: String
)