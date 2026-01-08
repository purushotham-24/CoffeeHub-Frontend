package com.simats.coffeehub.data.repository

import com.simats.coffeehub.data.network.RetrofitClient

class ProfileRepository {

    suspend fun getProfile(userId: Int) =
        RetrofitClient.api.getProfile(
            mapOf("user_id" to userId)
        )

    suspend fun updateProfile(
        userId: Int,
        name: String,
        email: String,
        phone: String,
        dob: String
    ) = RetrofitClient.api.updateProfile(
        mapOf(
            "user_id" to userId.toString(),
            "name" to name,
            "email" to email,
            "phone" to phone,
            "dob" to dob
        )
    )
}
