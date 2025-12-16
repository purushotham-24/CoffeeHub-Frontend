package com.example.coffeehub.screens.bookings

data class BookingItem(
    val type: String,              // "seat" or "workspace"
    val title: String,             // "Seat: A1,A2" OR "Solo Workspace"
    val date: String,
    val time: String
)
