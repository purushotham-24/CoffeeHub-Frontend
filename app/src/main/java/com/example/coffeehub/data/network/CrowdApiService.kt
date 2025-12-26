package com.example.coffeehub.data.network

import com.example.coffeehub.model.CrowdResponse
import retrofit2.http.GET

interface CrowdApiService {

    @GET("crowd_prediction.json")
    suspend fun getCrowdData(): CrowdResponse
}
