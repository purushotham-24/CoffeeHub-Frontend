package com.example.coffeehub.data.repository

import com.example.coffeehub.data.model.AppNotification
import com.example.coffeehub.data.model.NotificationIconMapper
import com.example.coffeehub.data.network.RetrofitClient

class NotificationRepository {

    suspend fun getNotifications(userId: Int): List<AppNotification> {

        val res = RetrofitClient.api.getNotifications(
            mapOf("user_id" to userId)
        )

        if (!res.status || res.data == null) return emptyList()

        return res.data.map { item: Map<String, Any> ->
            AppNotification(
                id = (item["id"] as Double).toInt(),
                title = item["title"] as String,
                message = item["message"] as String,
                time = item["time"] as String,
                unread = item["unread"] as Boolean,
                icon = NotificationIconMapper.map(item["title"] as String)
            )
        }
    }

    suspend fun clearAll(userId: Int): Boolean {
        val res = RetrofitClient.api.clearAllNotifications(
            mapOf("user_id" to userId)
        )
        return res.status
    }
}
