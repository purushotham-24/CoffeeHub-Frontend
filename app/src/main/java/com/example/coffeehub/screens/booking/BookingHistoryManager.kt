package com.example.coffeehub.screens.booking

import androidx.compose.runtime.mutableStateListOf

data class BookingHistory(
    val type: String,      // "seat" | "workspace"
    val title: String,     // "A1, A2" OR "Solo Workspace"
    val date: String,
    val time: String,
    // Stable unique id derived from fields so callers don't need to pass it explicitly
    val id: String = buildString {
        append(type)
        append("|")
        append(title)
        append("|")
        append(date)
        append("|")
        append(time)
    }
)

object BookingHistoryManager {

    val bookings = mutableStateListOf<BookingHistory>()

    fun addBookingOnce(booking: BookingHistory) {
        // 🔥 Prevent duplicates using stable id
        if (bookings.none { it.id == booking.id }) {
            bookings.add(0, booking) // latest on top
        }
    }

    fun removeBookingById(id: String) {
        val toRemove = bookings.find { it.id == id }
        if (toRemove != null) bookings.remove(toRemove)
    }
}
