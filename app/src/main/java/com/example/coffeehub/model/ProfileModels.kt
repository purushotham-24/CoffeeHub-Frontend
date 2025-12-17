package com.example.coffeehub.data.model

data class ProfileResponse(
    val status: Boolean,
    val data: ProfileData?
)

data class ProfileData(
    val name: String,
    val email: String,
    val phone: String,
    val dob: String
)

data class UpdateProfileResponse(
    val status: Boolean,
    val message: String
)
