package com.simats.coffeehub.data.model

data class PlaceOrderRequest(
    val user_id: Int,
    val order_id: String,
    val items: Int,
    val total: Int,
    val status: String
)