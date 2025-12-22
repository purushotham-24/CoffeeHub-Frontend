package com.example.coffeehub.screens.booking

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

    // 🔒 Lock seats after booking
    fun occupySeats(bookedSeats: List<String>) {
        seats.forEach { seat ->
            if (seat.id in bookedSeats) {
                seat.status = "occupied"
            }
        }

        // ♻ Optional auto-reset
        if (seats.all { it.status == "occupied" }) {
            resetSeats()
        }
    }

    // 🔓 Free seats after cancellation
    fun freeSeats(seatIds: List<String>) {
        seats.forEach { seat ->
            if (seat.id in seatIds) {
                seat.status = "available"
            }
        }
    }

    // ♻ Reset all seats
    private fun resetSeats() {
        seats.clear()
        seatIds.forEach {
            seats.add(Seat(it, "available"))
        }
    }
}