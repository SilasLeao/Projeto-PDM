package com.example.acompanhapp.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: String,
    val nome: String,
    val email: String,
    val username: String,
    val password: String
)
