package com.example.acompanhapp.model

data class Medico(
    val id: String,
    val nome: String,
    val email: String,
    val hospitalId: String,
    val cargo: String,
    val setor: String,
    val username: String,
    val password: String,
    val authoritiesAsStrings: List<String>
)