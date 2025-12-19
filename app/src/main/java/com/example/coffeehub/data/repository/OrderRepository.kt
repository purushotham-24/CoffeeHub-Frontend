package com.example.coffeehub.data.repository

import com.example.coffeehub.data.network.RetrofitClient

class OrderRepository {

    suspend fun placeOrder(
        userId: Int,
        orderId: String,
        items: Int,
        total: Int,
        status: String = "Completed"
    ) = RetrofitClient.api.placeOrder(
        mapOf(
            "user_id" to userId,
            "order_id" to orderId,
            "items" to items,
            "total" to total,
            "status" to status
        )
    )

    suspend fun getOrderHistory(userId: Int) =
        RetrofitClient.api.getOrderHistory(userId)
}
