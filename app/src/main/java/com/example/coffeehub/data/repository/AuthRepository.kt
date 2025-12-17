package com.example.coffeehub.data.repository

import com.example.coffeehub.data.network.RetrofitClient

class AuthRepository {

    suspend fun login(email: String, password: String) =
        RetrofitClient.api.login(
            mapOf(
                "email" to email,
                "password" to password
            )
        )

    suspend fun register(
        name: String,
        email: String,
        password: String,
        phone: String,
        dob: String
    ) = RetrofitClient.api.register(
        mapOf(
            "name" to name,
            "email" to email,
            "password" to password,
            "phone" to phone,
            "dob" to dob
        )
    )

    suspend fun forgotPassword(email: String) =
        RetrofitClient.api.forgotPassword(
            mapOf("email" to email)
        )

    // ✅ OTP BASED RESET (FINAL)
    suspend fun resetPassword(
        email: String,
        otp: String,
        password: String
    ) =
        RetrofitClient.api.resetPassword(
            mapOf(
                "email" to email,
                "otp" to otp,
                "password" to password
            )
        )
}
