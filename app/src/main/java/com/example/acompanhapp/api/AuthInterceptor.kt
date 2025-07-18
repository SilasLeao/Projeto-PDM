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
            .addHeader("Authorization", "Bearer eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJhY29tcGFuaGFwcCIsInN1YiI6ImFkbWluQGVtYWlsLmNvbSIsImV4cCI6MTc1Mjg4NDI0MSwiaWF0IjoxNzUyODgwNjQxLCJzY29wZSI6IlJPTEVfQURNSU4ifQ.NJASktnj4VIEnzXsVVByed888-QFTrFOsmMoVJnw_UmIw4mIlDFJpYmG8hFpfznPn920fBGVVZUo8LxME48KrW_TO9pQUOmiIKY_e94-zJ4JIUqO2BsBaV8h_ajzFjOog0_14Ss53-4CrRERQS-gpnVPsDOQH04kdh5bmJHIQJ9tT7RgVIDvKzLtSb_Kxw7SGdKNqViorcGwUuuTXYyhdPMeYN5gV9E1osEn7TSbCE9WaZkNw4gzoLu0ibjaCcXJ-3X6GkBQbZjYJdSQluFnb6PnTCK0h-ccoaqcQ1kF2HcAOuvB-RoqrFvpdq2xpuvu6tMx5rxXCmW7wEt6lAHVRw")
            .build()
        return chain.proceed(requestWithAuth)
    }

}
