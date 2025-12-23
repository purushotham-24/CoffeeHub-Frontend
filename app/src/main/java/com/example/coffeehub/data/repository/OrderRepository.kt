package com.example.coffeehub.data.repository

import com.example.coffeehub.data.model.PlaceOrderRequest
import com.example.coffeehub.data.network.RetrofitClient

class OrderRepository {

    suspend fun placeOrder(
        userId: Int,
        orderId: String,
        items: Int,
        total: Int,
        status: String = "Completed"
    ) = RetrofitClient.api.placeOrder(
        PlaceOrderRequest(
            user_id = userId,
            order_id = orderId,
            items = items,
            total = total,
            status = status
        )
    )

    suspend fun getOrderHistory(userId: Int) =
        RetrofitClient.api.getOrderHistory(userId)
}