package com.example.acompanhapp.api

import android.util.Log
import okhttp3.Request
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor

class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestWithAuth = chain.request()
            .newBuilder()
            .addHeader("Authorization", "Bearer eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJhY29tcGFuaGFwcCIsInN1YiI6ImFkbWluQGVtYWlsLmNvbSIsImV4cCI6MTc1Mjg4MDUyNSwiaWF0IjoxNzUyODc2OTI1LCJzY29wZSI6IlJPTEVfQURNSU4ifQ.vKap1hKAJQyJQuLyNLwIu8HC2wZSoY6miuJkF8_VGdUlokaCfpbGDaqmq2e09vCuQqOdrKiSxpTAm8tGlbQ7vMG5ClnqF5-b7KyZvCFm-GR4U1WtfkCxaZhuDDnAbzfnlsej7TMd_9WrkLFnxwZEZr1tr0IpoG8LsJmuNQxlk4T_Rf57ttR24v7hQui63F4sP3BB5BEDhl_k7lpMtvNZGrJA7XcMLkv7g9NFy_DnRv9QIBo59mz0K567bsJITZvMpc_1rsfpJ9ZNwb7DpBVh-_OVEn-z_gKJ-C6qgDEUQ9rgk4QDfGfMYjpBiLu3FybiE1szhvZaZPUF9vTIDO_xhg")
            .build()
        return chain.proceed(requestWithAuth)
    }

}
