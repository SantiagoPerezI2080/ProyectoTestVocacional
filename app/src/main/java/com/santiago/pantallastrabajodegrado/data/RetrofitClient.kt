package com.santiago.pantallastrabajodegrado.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    // --- CORRECCIÓN AQUÍ: URL Base actualizada a tu Gist correcto ---
    private const val BASE_URL = "https://gist.githubusercontent.com/Brubytec/1f11d943c20ab0252e782fd5fb6c936c/raw/6c81139acbac53dee37aacc5a3b9231fe6a6d5fd/"

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}