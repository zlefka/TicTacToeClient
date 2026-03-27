package com.example.tic_tac_toe_mobile.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

object RetrofitController {
    fun createApi(credentialsProvider: () -> Pair<String?, String?>): GameApi {
        val authInterceptor = BasicAuthInterceptor(credentialsProvider)

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }

        val client = OkHttpClient.Builder().addInterceptor(authInterceptor).addInterceptor(loggingInterceptor).build()

        val retrofit = Retrofit.Builder().baseUrl("http://10.0.2.2:8080").client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


        return retrofit.create(GameApi::class.java)

    }
}