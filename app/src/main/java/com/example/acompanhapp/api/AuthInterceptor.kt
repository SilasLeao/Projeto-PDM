package com.example.acompanhapp.api

import okhttp3.Interceptor
import okhttp3.Response

// Interceptor para adicionar o cabeçalho de autorização com token Bearer em todas as requisições HTTP feitas pelo Retrofit.
class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestWithAuth = chain.request()
            .newBuilder()
            // Necessário trocar a palavra "token" pelo token de autorização
            .addHeader("Authorization", "Bearer eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJhY29tcGFuaGFwcCIsInN1YiI6ImFkbWluQGVtYWlsLmNvbSIsImV4cCI6MTc1NjQxODc0MSwiaWF0IjoxNzU2NDE1MTQxLCJzY29wZSI6IlJPTEVfQURNSU4ifQ.k2w0F_1TbDfTLUvoJbtXh5hzcvB8QGzU3v4f8uXt8J8ygP7sYXRMg7vGtxPTfcq0BBP8HcWhMFIe9N-Xf70mclLS3d5EP8VdP0ojjUugZFqhTvGjOnbh31PYRsGjqy4kbOpUVlm2C2zsXU3EsWq72cyukIA8WiA2A3OnqQ0J3JWksOBNMziq5jqBN34IJJmoh6MkwcqREwAaUY8XIbSeZLN922aHL7Ur1R7vgfotPVay50tmwD1Pb7g8r8u9nWGukr-rd74b1cy8jXAoLXR-JCUXERFWorJBXum1wQvldk40mK9TiO-vu5yYWnx28uszBH-Yg0TUZO8qrKZHTznPUQ")
            .build()
        return chain.proceed(requestWithAuth)
    }

}
