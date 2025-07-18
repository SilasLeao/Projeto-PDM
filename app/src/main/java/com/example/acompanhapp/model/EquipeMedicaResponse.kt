package com.example.acompanhapp.model

data class EquipeMedicaResponse(
    val status: String,
    val message: String,
    val data: List<Medico>
)