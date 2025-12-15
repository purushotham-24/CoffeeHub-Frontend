package com.example.coffeehub.cart

data class CartItem(
    val id: String,
    val name: String,
    val size: String,
    val price: Int,
    val quantity: Int
)
