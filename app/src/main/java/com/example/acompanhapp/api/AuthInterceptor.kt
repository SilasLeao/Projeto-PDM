package com.example.acompanhapp.api

import okhttp3.Interceptor
import okhttp3.Response

// Interceptor para adicionar o cabeçalho de autorização com token Bearer em todas as requisições HTTP feitas pelo Retrofit.
class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestWithAuth = chain.request()
            .newBuilder()
            // Necessário trocar a palavra "token" pelo token de autorização
            .addHeader("Authorization", "Bearer eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJhY29tcGFuaGFwcCIsInN1YiI6ImFkbWluQGVtYWlsLmNvbSIsImV4cCI6MTc1NjQyNjM2NCwiaWF0IjoxNzU2NDIyNzY0LCJzY29wZSI6IlJPTEVfQURNSU4ifQ.Wb2-w_PMrfO5c0rb0N4eV10NIrnl3wZ8mRvDOamitGOUagF66dq1rJTjCy7W9ohBA5_DOvQBrNX88qYAR9yOb9edqDWsZqNqiKObQ4ENOIuoOSb0GWahakPVoeRLF5Gd79vkd4a0jXA17-ivTd_CoEukyOGnVVOqmAorkIs2jzBmX7ic_pxBLpN-qoOrjIJUn2pqxOIMo8BtmBU4W_kfjz--lG2AUzo5unyIl3UO1BLbg0YGurEj7bIR3L669ImaTx9TgjdBgf2BCmNUnfzPDdKtcYcacuvi0WiE0SIf6Zas4CEwRDMdRqNA25BMjO9g4v91Jgv8CNJcPqv7ji7djg")
            .build()
        return chain.proceed(requestWithAuth)
    }

}
