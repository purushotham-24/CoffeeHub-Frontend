
package com.simats.coffeehub.data.model

data class PlaceBookingRequest(
    val user_id: Int,
    val type: String,
    val title: String,
    val date: String,
    val time: String,
    val seats: List<String>
)
