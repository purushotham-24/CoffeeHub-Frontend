package com.example.coffeehub.screens.orders

import androidx.compose.runtime.mutableStateListOf
import java.text.SimpleDateFormat
import java.util.*

data class OrderItem(
    val id: String,
    val date: String,
    val time: String,
    val items: Int,
    val total: Int,
    val status: String
)

object OrderHistoryManager {

    val orders = mutableStateListOf<OrderItem>()
    private var lastOrderId: String? = null

    fun addOrder(
        id: String,
        items: Int,
        total: Int,
        status: String = "Completed"
    ) {
        // prevent duplicate
        if (lastOrderId == id) return
        lastOrderId = id

        val date = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(Date())
        val time = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())

        orders.add(
            0,
            OrderItem(
                id = id,
                date = date,
                time = time,
                items = items,
                total = total,
                status = status
            )
        )
    }

    fun clear() {
        orders.clear()
        lastOrderId = null
    }
}
