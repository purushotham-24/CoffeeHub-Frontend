package com.example.coffeehub.data.repository

import com.example.coffeehub.data.model.PlaceBookingRequest
import com.example.coffeehub.data.network.RetrofitClient

class BookingRepository {

    suspend fun placeBooking(
        userId: Int,
        type: String,
        title: String,
        date: String,
        time: String,
        seats: List<String>
    ) =
        RetrofitClient.api.placeBooking(
            PlaceBookingRequest(
                user_id = userId,
                type = type,
                title = title,
                date = date,
                time = time,
                seats = seats
            )
        )

    suspend fun getBookings(userId: Int) =
        RetrofitClient.api.getBookings(userId)

    suspend fun cancelBooking(bookingId: Int) =
        RetrofitClient.api.cancelBooking(
            mapOf("booking_id" to bookingId)
        )
}
