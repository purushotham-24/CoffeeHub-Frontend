package com.simats.coffeehub.data.network

import com.simats.coffeehub.model.CrowdResponse
import retrofit2.http.GET

interface CrowdApiService {

    @GET("crowd_prediction.json")
    suspend fun getCrowdData(): CrowdResponse
}
