package com.example.coffeehub.model

data class CrowdResponse(
    val Today: List<Int>,
    val Tomorrow: List<Int>,
    val Friday: List<Int>,
    val Saturday: List<Int>,
    val Sunday: List<Int>
)
