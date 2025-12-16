package com.example.coffeehub.profile

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

object OrderManager {

    val orders = mutableStateListOf<OrderItem>()

    fun addOrder(itemsCount: Int, totalAmount: Int) {
        val now = Date()
        val dateFmt = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
        val timeFmt = SimpleDateFormat("hh:mm a", Locale.getDefault())

        orders.add(
            0, // latest order on top
            OrderItem(
                id = "#ORD${System.currentTimeMillis()}",
                date = dateFmt.format(now),
                time = timeFmt.format(now),
                items = itemsCount,
                total = totalAmount,
                status = "Completed"
            )
        )
    }
}
