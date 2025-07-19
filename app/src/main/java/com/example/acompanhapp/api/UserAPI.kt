package com.example.acompanhapp.api

import com.example.acompanhapp.model.EquipeMedicaResponse
import com.example.acompanhapp.model.PacienteResponse
import com.example.acompanhapp.model.UserResponse
import retrofit2.http.GET
import retrofit2.Call

// Interface que define os endpoints da API REST para requisições relacionadas a usuários, equipe médica e pacientes.
interface UserApi {
    @GET("familiar")
    fun getUsers(): Call<UserResponse>

    @GET("equipemedica")
    fun getEquipeMedica(): Call<EquipeMedicaResponse>

    @GET("paciente")
    fun getPacientes(): Call<PacienteResponse>
}
