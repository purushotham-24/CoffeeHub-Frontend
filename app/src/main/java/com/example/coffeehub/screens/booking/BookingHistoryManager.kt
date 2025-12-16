package com.example.coffeehub.screens.booking

import androidx.compose.runtime.mutableStateListOf

data class BookingHistory(
    val type: String,      // "seat" | "workspace"
    val title: String,     // "A1, A2" OR "Solo Workspace"
    val date: String,
    val time: String
)

object BookingHistoryManager {

    val bookings = mutableStateListOf<BookingHistory>()

    fun addBookingOnce(booking: BookingHistory) {
        // 🔥 Prevent duplicates
        if (bookings.none {
                it.type == booking.type &&
                        it.title == booking.title &&
                        it.date == booking.date &&
                        it.time == booking.time
            }
        ) {
            bookings.add(0, booking) // latest on top
        }
    }
}
