package com.example.coffeehub.data.network

import com.example.coffeehub.data.model.ApiResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("auth/login.php")
    suspend fun login(
        @Body body: Map<String, String>
    ): ApiResponse<Map<String, Any>>

    @POST("auth/register.php")
    suspend fun register(
        @Body body: Map<String, String>
    ): ApiResponse<Map<String, Any>>

    @POST("auth/forgot_password.php")
    suspend fun forgotPassword(
        @Body body: Map<String, String>
    ): ApiResponse<Any>

    @POST("auth/reset_password.php")
    suspend fun resetPassword(
        @Body body: Map<String, String>
    ): ApiResponse<Any>
}
