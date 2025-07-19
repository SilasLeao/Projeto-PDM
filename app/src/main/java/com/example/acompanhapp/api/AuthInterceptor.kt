package com.example.acompanhapp.api

import okhttp3.Interceptor
import okhttp3.Response

// Interceptor para adicionar o cabeçalho de autorização com token Bearer em todas as requisições HTTP feitas pelo Retrofit.
class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestWithAuth = chain.request()
            .newBuilder()
            // Necessário trocar a palavra "token" pelo token de autorização
            .addHeader("Authorization", "Bearer token")
            .build()
        return chain.proceed(requestWithAuth)
    }

}
