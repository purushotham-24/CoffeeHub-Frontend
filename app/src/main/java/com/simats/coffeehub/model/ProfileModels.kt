package com.simats.coffeehub.data.model

/* ---------- GET PROFILE ---------- */

data class ProfileResponse(
    val status: Boolean,
    val data: ProfileData?
)

/**
 * Nullable fields because:
 * - phone can be NULL for new users
 * - dob can be NULL for new users
 */
data class ProfileData(
    val name: String?,
    val email: String?,
    val phone: String?,
    val dob: String?
)

/* ---------- UPDATE PROFILE ---------- */

data class UpdateProfileRequest(
    val user_id: Int,
    val name: String,
    val email: String,
    val phone: String?, // nullable allowed
    val dob: String?    // nullable allowed
)

data class UpdateProfileResponse(
    val status: Boolean,
    val message: String
)
