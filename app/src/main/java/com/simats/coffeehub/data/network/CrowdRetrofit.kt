package com.simats.coffeehub.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CrowdRetrofit {

    private const val BASE_URL =
        "https://raw.githubusercontent.com/purushotham-24/coffeehub_crowd_data/main/"

    val api: CrowdApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CrowdApiService::class.java)
    }
}
