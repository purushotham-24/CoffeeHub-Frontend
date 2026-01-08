package com.simats.coffeehub.model

data class Coffee(
    val id: String,
    val name: String,
    val description: String,
    val price: Int,
    val image: String,
    val category: String
)
