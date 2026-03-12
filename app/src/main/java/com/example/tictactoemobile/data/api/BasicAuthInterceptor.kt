package com.example.tic_tac_toe_mobile.data.api

import okhttp3.Interceptor
import okhttp3.Response
import java.util.Base64

class BasicAuthInterceptor (
    private val credentials: () -> Pair<String?, String?>
        ): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val (login, password) = credentials()

        if(!login.isNullOrBlank() && !password.isNullOrBlank()) {
            val cred = "$login:$password"
            val encodedCredentials = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                Base64.getEncoder().encodeToString(cred.toByteArray())
            } else {
                android.util.Base64.encodeToString(cred.toByteArray(), android.util.Base64.NO_WRAP)
            }
            val newRequest = request.newBuilder().header("Authorization", "Basic $encodedCredentials")
                .build()
            return chain.proceed(newRequest)
        }
        return chain.proceed(request)
    }

}