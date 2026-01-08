package com.simats.coffeehub.screens.booking

import androidx.compose.runtime.mutableStateListOf

object SeatManager {

    private val seatIds = listOf(
        "A1","A2","A3","A4",
        "B1","B2","B3","B4",
        "C1","C2","C3","C4",
        "D1","D2","D3","D4"
    )

    val seats = mutableStateListOf<Seat>()

    init {
        resetSeats()
    }

    /**
     * Call this AFTER booking confirmation
     */
    fun occupySeats(bookedSeats: List<String>) {

        // 🔒 Mark ONLY booked seats as occupied
        seats.forEach { seat ->
            if (seat.id in bookedSeats) {
                seat.status = "occupied"
            }
        }

        // ✅ Reset ONLY when all seats are occupied
        if (seats.all { it.status == "occupied" }) {
            resetSeats()
        }
    }

    // ♻ Reset seats for next slot
    private fun resetSeats() {
        seats.clear()
        seatIds.forEach {
            seats.add(Seat(it, "available"))
        }
    }
}
