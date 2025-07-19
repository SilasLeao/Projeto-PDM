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
            .addHeader("Authorization", "Bearer eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJhY29tcGFuaGFwcCIsInN1YiI6ImFkbWluQGVtYWlsLmNvbSIsImV4cCI6MTc1Mjk0MDkwNywiaWF0IjoxNzUyOTM3MzA3LCJzY29wZSI6IlJPTEVfQURNSU4ifQ.sCjfE_A5mfgaIFH7qkLirSkQ1SX8E_B-9WDyeoTOzQN0R85ocYfnQVSWt6RUt6yrW2adLPwPKUal6ZRtyJa7ecgpmNlWSOUGb_JoTKsgQ7b79UoRFLik10OmLq5X5KEtUZ61xth4h1qbIwbnSiRdTzu-dXpCYqaQFVM3YTFhuWww-pKFsVJzf8p7mLzWDU-5lpYiTd69bljSQyu-BFJVwr9ocaKnPYUTrWzZ_1uYtP9rhh1o_W-M9Wv9xytM6rMi-JxrSDWfYNZioZL5Hpequ4eN_oDtTzZJmrVcoLstyJyyH7HwqBhtWSEnNuM1UXq56mR9hjyBBCVvKV_cnM4uTA")
            .build()
        return chain.proceed(requestWithAuth)
    }

}
