package com.example.coffeehub.screens.prediction

import com.example.coffeehub.screens.booking.BookingHistoryManager

private const val MAX_CAPACITY = 50  // café capacity

fun calculateCurrentCrowd(): Int {
    var peopleCount = 0

    BookingHistoryManager.bookings.forEach { booking ->
        peopleCount += when {
            booking.type == "seat" -> 1
            booking.title.contains("Solo", true) -> 1
            booking.title.contains("Duo", true) -> 2
            booking.title.contains("Team", true) -> 5
            else -> 1
        }
    }

    return ((peopleCount.toFloat() / MAX_CAPACITY) * 100)
        .toInt()
        .coerceIn(5, 100)
}

fun crowdLabel(level: Int): String = when {
    level >= 90 -> "Peak"
    level >= 70 -> "High"
    level >= 40 -> "Medium"
    else -> "Low"
}
