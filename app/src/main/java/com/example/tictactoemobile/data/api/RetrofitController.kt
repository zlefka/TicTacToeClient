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

        val client = OkHttpClient.Builder().addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor).build()

        val retrofit = Retrofit.Builder().baseUrl(ApiConfig.BASE_URL).client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


        return retrofit.create(GameApi::class.java)

    }
}

object ApiConfig {
    private const val EMULATOR_IP = "10.0.2.2"
    private const val LOCAL_IP = "192.168.31.75"

    private const val PORT = 8080

    val BASE_URL: String
        get() = if (isEmulator()) {
            "http://$EMULATOR_IP:$PORT/"
        } else {
            "http://$LOCAL_IP:$PORT/"
        }

    private fun isEmulator(): Boolean {
        return (android.os.Build.FINGERPRINT.startsWith("google/sdk_gphone")
                || android.os.Build.FINGERPRINT.startsWith("generic")
                || android.os.Build.FINGERPRINT.contains("emulator")
                || android.os.Build.MODEL.contains("Emulator")
                || android.os.Build.MODEL.contains("Android SDK built for x86"))
    }
}