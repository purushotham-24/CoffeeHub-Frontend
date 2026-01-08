package com.simats.coffeehub.screens.booking

import androidx.compose.runtime.mutableStateOf

object BookingManager {

    // booking type
    val bookingType = mutableStateOf("") // "seat" | "workspace"

    // seat booking
    val selectedSeats = mutableStateOf<List<String>>(emptyList())

    // workspace booking
    val workspaceId = mutableStateOf<String?>(null)
    val workspaceName = mutableStateOf<String?>(null)

    // date & time
    val selectedDate = mutableStateOf("")
    val selectedTime = mutableStateOf("")

    fun clear() {
        bookingType.value = ""
        selectedSeats.value = emptyList()
        workspaceId.value = null
        workspaceName.value = null
        selectedDate.value = ""
        selectedTime.value = ""
    }
}
