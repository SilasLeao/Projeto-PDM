package com.example.acompanhapp.api

import okhttp3.Interceptor
import okhttp3.Response

// Interceptor para adicionar o cabeçalho de autorização com token Bearer em todas as requisições HTTP feitas pelo Retrofit.
class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestWithAuth = chain.request()
            .newBuilder()
            // Necessário trocar a palavra "token" pelo token de autorização
            .addHeader("Authorization", "Bearer eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJhY29tcGFuaGFwcCIsInN1YiI6ImFkbWluQGVtYWlsLmNvbSIsImV4cCI6MTc1NjQyMjUwMiwiaWF0IjoxNzU2NDE4OTAyLCJzY29wZSI6IlJPTEVfQURNSU4ifQ.C3kSlPHAcYzXXqYmHiQ6P0hPduZ9WEO0DKZjYOsr_L-7WspYnS06KVvHzyALpjgJJCm4fm3E3DXc-vCVJVeWS9exWzYdWkKBvTVHltKMpxGk7dls4V0Pr2b_pRTb_kafBdBycDaJuDhtPQWp4NxnIfPV0rvp0a5I43e2OEM7iXCfChHiWh6Stv4s9BsUjxWnU8doTgpWtGq3Kxxs0Y2nBN3nbfuuefZxy82v2uwUmmat_5zYGGlPm-X3bNiYBAJOA49_Hh2HIk7SfXCedNT6QDP0upsUPtSM-iYiB5Pol60VvaH0CljXUOWxNMmXa0_59ioC4pLra_qZRJhATjF5yw")
            .build()
        return chain.proceed(requestWithAuth)
    }

}
