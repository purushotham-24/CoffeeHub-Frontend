package com.example.coffeehub.data.network

import com.example.coffeehub.data.model.ApiResponse
import com.example.coffeehub.data.model.ProfileResponse
import com.example.coffeehub.data.model.UpdateProfileResponse
import com.example.coffeehub.data.model.PlaceOrderRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Headers

interface ApiService {

    /* ---------------- AUTH ---------------- */

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

    /* ---------------- PROFILE ---------------- */

    @POST("profile/get_profile.php")
    suspend fun getProfile(
        @Body body: Map<String, Int>
    ): ProfileResponse

    @POST("profile/update_profile.php")
    suspend fun updateProfile(
        @Body body: Map<String, String>
    ): UpdateProfileResponse

    /* ---------------- NOTIFICATIONS ---------------- */

    @POST("notifications/get_notifications.php")
    suspend fun getNotifications(
        @Body body: Map<String, Int>
    ): ApiResponse<List<Map<String, Any>>>

    @POST("notifications/clear_all.php")
    suspend fun clearAllNotifications(
        @Body body: Map<String, Int>
    ): ApiResponse<Any>

    /* ---------------- ORDERS ---------------- */

    @Headers("Content-Type: application/json")
    @POST("orders/place_order.php")
    suspend fun placeOrder(
        @Body body: PlaceOrderRequest
    ): ApiResponse<Any>

    @GET("orders/order_history.php")
    suspend fun getOrderHistory(
        @Query("user_id") userId: Int
    ): ApiResponse<List<Map<String, Any>>>
}
