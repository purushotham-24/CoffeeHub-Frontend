package com.example.coffeehub.model

data class Coffee(
    val id: String,
    val name: String,
    val description: String,
    val price: Int,
    val rating: Double,
    val image: String
)
