package com.simats.coffeehub.data.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*

object NotificationIconMapper {
    fun map(title: String) = when {
        title.contains("Order", true) -> Icons.Default.Coffee
        title.contains("Booking", true) -> Icons.Default.EventSeat
        title.contains("Welcome", true) -> Icons.Default.Celebration
        else -> Icons.Default.Notifications
    }
}
