package com.example.acompanhapp.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "visitas")
data class VisitaEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val pacienteId: String,
    val usuarioId: String,
    val dataHora: String
)
