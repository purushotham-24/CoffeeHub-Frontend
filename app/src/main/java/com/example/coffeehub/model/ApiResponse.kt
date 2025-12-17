package com.example.coffeehub.data.model

data class ApiResponse<T>(
    val status: Boolean,
    val data: T?,
    val message: String
)
