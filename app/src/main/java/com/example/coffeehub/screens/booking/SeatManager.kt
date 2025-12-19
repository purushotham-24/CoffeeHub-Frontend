package com.example.coffeehub.screens.booking

import androidx.compose.runtime.mutableStateListOf

data class Seat(
    val id: String,
    var status: String // "available" | "occupied"
)

object SeatManager {

    // 🔥 ALL SEATS START AS AVAILABLE
    val seats = mutableStateListOf<Seat>().apply {
        listOf(
            "A1","A2","A3","A4",
            "B1","B2","B3","B4",
            "C1","C2","C3","C4",
            "D1","D2","D3","D4"
        ).forEach {
            add(Seat(it, "available"))
        }
    }

    // ✅ MARK SEATS AS OCCUPIED AFTER SUCCESSFUL BOOKING
    fun occupySeats(seatIds: List<String>) {
        seats.forEach { seat ->
            if (seat.id in seatIds) {
                seat.status = "occupied"
            }
        }
    }
}
