package com.example.acompanhapp.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Cliente Retrofit para realizar chamadas HTTP à API, incluindo o interceptor de autenticação
object RetrofitClient {
    private const val BASE_URL = "http://3.142.172.75:8080/"

    fun getClient(): UserApi {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor()) // Adiciona interceptor para autenticação
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserApi::class.java) // Cria implementação da interface UserApi que possui os endpoints
    }
}
