package com.simats.coffeehub.screens.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.mutableStateListOf
import java.text.SimpleDateFormat
import java.util.*

/* ---------- DATA MODEL ---------- */
data class AppNotification(
    val title: String,
    val message: String,
    val time: String,
    val unread: Boolean = true,
    val icon: androidx.compose.ui.graphics.vector.ImageVector = Icons.Default.Notifications
)

/* ---------- MANAGER ---------- */
object NotificationManager {

    // Observable list → UI updates automatically
    val notifications = mutableStateListOf<AppNotification>()

    fun addBookingNotification(title: String, message: String) {

        // ❌ prevent duplicate same notification
        if (notifications.any { it.title == title && it.message == message }) return

        val time = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())

        notifications.add(
            0, // newest on top
            AppNotification(
                title = title,
                message = message,
                time = time
            )
        )
    }

    fun clearAll() {
        notifications.clear()
    }
}
