package com.simats.coffeehub.data.model

import androidx.compose.ui.graphics.vector.ImageVector

data class AppNotification(
    val id: Int,
    val title: String,
    val message: String,
    val time: String,
    val unread: Boolean,
    val icon: ImageVector
)
