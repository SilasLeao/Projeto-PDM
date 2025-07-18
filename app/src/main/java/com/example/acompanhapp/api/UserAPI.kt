package com.example.acompanhapp.api

import com.example.acompanhapp.model.EquipeMedicaResponse
import com.example.acompanhapp.model.UserResponse
import retrofit2.http.GET
import retrofit2.Call
interface UserApi {
    @GET("familiar")
    fun getUsers(): Call<UserResponse>

    @GET("equipemedica")
    fun getEquipeMedica(): Call<EquipeMedicaResponse>
}
