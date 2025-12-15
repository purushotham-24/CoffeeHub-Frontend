package com.example.coffeehub.cart

import androidx.compose.runtime.mutableStateListOf

object CartManager {

    // ✅ GLOBAL CART STATE (Compose aware)
    val cartItems = mutableStateListOf<CartItem>()

    fun addItem(
        id: String,
        name: String,
        size: String,
        price: Int,
        quantity: Int
    ) {
        val existing = cartItems.find { it.id == id && it.size == size }

        if (existing != null) {
            val index = cartItems.indexOf(existing)
            cartItems[index] =
                existing.copy(quantity = existing.quantity + quantity)
        } else {
            cartItems.add(
                CartItem(id, name, size, price, quantity)
            )
        }
    }

    fun updateQty(id: String, change: Int) {
        val index = cartItems.indexOfFirst { it.id == id }
        if (index != -1) {
            val item = cartItems[index]
            val newQty = (item.quantity + change).coerceAtLeast(1)
            cartItems[index] = item.copy(quantity = newQty)
        }
    }

    fun removeItem(id: String) {
        cartItems.removeAll { it.id == id }
    }

    fun clear() {
        cartItems.clear()
    }
}
