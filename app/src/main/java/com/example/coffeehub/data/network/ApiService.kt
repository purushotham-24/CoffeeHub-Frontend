package com.example.coffeehub.data.network

import com.example.coffeehub.data.model.*
import com.example.coffeehub.model.Coffee
import retrofit2.http.*

interface ApiService {

    /* ================= AUTH ================= */

    @Headers("Content-Type: application/json")
    @POST("auth/login.php")
    suspend fun login(
        @Body body: Map<String, String>
    ): ApiResponse<Map<String, Any>>

    @Headers("Content-Type: application/json")
    @POST("auth/register.php")
    suspend fun register(
        @Body body: Map<String, String>
    ): ApiResponse<Map<String, Any>>

    @Headers("Content-Type: application/json")
    @POST("auth/forgot_password.php")
    suspend fun forgotPassword(
        @Body body: Map<String, String>
    ): ApiResponse<Any>

    @Headers("Content-Type: application/json")
    @POST("auth/reset_password.php")
    suspend fun resetPassword(
        @Body body: Map<String, String>
    ): ApiResponse<Any>


    /* ================= PROFILE ================= */

    @Headers("Content-Type: application/json")
    @POST("profile/get_profile.php")
    suspend fun getProfile(
        @Body body: Map<String, Int>
    ): ProfileResponse

    @Headers("Content-Type: application/json")
    @POST("profile/update_profile.php")
    suspend fun updateProfile(
        @Body body: Map<String, String>
    ): UpdateProfileResponse


    /* ================= NOTIFICATIONS ================= */

    @Headers("Content-Type: application/json")
    @POST("notifications/get_notifications.php")
    suspend fun getNotifications(
        @Body body: Map<String, Int>
    ): ApiResponse<List<Map<String, Any>>>

    @Headers("Content-Type: application/json")
    @POST("notifications/clear_all.php")
    suspend fun clearAllNotifications(
        @Body body: Map<String, Int>
    ): ApiResponse<Any>


    /* ================= ORDERS ================= */

    @Headers("Content-Type: application/json")
    @POST("orders/place_order.php")
    suspend fun placeOrder(
        @Body body: PlaceOrderRequest
    ): ApiResponse<Any>

    @Headers("Content-Type: application/json")
    @GET("orders/order_history.php")
    suspend fun getOrderHistory(
        @Query("user_id") userId: Int
    ): ApiResponse<List<Map<String, Any>>>


    /* ================= BOOKINGS ================= */

    @Headers("Content-Type: application/json")
    @POST("bookings/place_booking.php")
    suspend fun placeBooking(
        @Body body: PlaceBookingRequest
    ): ApiResponse<Any>

    @Headers("Content-Type: application/json")
    @GET("bookings/get_bookings.php")
    suspend fun getBookings(
        @Query("user_id") userId: Int
    ): ApiResponse<List<Map<String, Any>>>

    @Headers("Content-Type: application/json")
    @POST("bookings/cancel_booking.php")
    suspend fun cancelBooking(
        @Body body: Map<String, Int>
    ): ApiResponse<Any>


    /* ================= SEATS ================= */

    @Headers("Content-Type: application/json")
    @GET("bookings/get_seats.php")
    suspend fun getSeats(): ApiResponse<List<Map<String, String>>>


    /* ================= COFFEES (NEW) ================= */
    /* ================= COFFEES ================= */

    @GET("coffees/get_coffees.php")
    suspend fun getAllCoffees(): List<Coffee>

    @POST("coffees/add_coffee.php")
    suspend fun addCoffee(@Body coffee: Coffee): ApiResponse<Unit>

    @POST("coffees/update_coffee.php")
    suspend fun updateCoffee(@Body coffee: Coffee): ApiResponse<Unit>

    @POST("coffees/delete_coffee.php")
    suspend fun deleteCoffee(
        @Query("id") id: String
    ): ApiResponse<Unit>

    @POST("payments/save_order.php")
    suspend fun saveOrder(
        @Body request: PlaceOrderRequest
    ): ApiResponse<Any>





}
