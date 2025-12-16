package com.example.coffeehub.cart

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf

object CartManager {

    // 🟢 GLOBAL CART (Compose aware)
    val cartItems = mutableStateListOf<CartItem>()

    // 🟡 PROMO STATE (NEW – SAFE)
    val appliedPromoCode = mutableStateOf<String?>(null)
    val discountAmount = mutableStateOf(0)

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
        appliedPromoCode.value = null
        discountAmount.value = 0
    }

    // 🔥 AMOUNT CALCULATION
    val subtotal: Int
        get() = cartItems.sumOf { it.price * it.quantity }

    val tax: Int
        get() = (subtotal * 0.05).toInt()

    val totalBeforeDiscount: Int
        get() = subtotal + tax

    val totalAmount: Int
        get() = (totalBeforeDiscount - discountAmount.value).coerceAtLeast(0)

    // 🎟 APPLY PROMO (FRONTEND ONLY)
    fun applyPromo(code: String) {
        discountAmount.value = when (code) {
            "COFFEE20" -> (totalBeforeDiscount * 0.20).toInt()
            "FIRST50" -> 50
            "WEEKEND30" -> (totalBeforeDiscount * 0.30).toInt()
            else -> 0
        }
        appliedPromoCode.value = code
    }
}
