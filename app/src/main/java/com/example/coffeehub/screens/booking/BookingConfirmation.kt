package com.example.coffeehub.screens.booking

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun BookingConfirmation(nav: NavController) {

    val isSeatBooking = BookingManager.bookingType.value == "seat"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        // ---------- TOP BAR ----------
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF5C4033))
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(26.dp)
                    .clickable { nav.popBackStack() }
            )

            Spacer(Modifier.width(12.dp))

            Text(
                "Booking Confirmation",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        // ---------- CONTENT ----------
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // ---------- MAIN CARD ----------
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(Color(0xFFF5E6CF))
            ) {
                Column(Modifier.padding(16.dp)) {

                    if (isSeatBooking) {
                        Text(
                            "Seat Reservation",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF5C4033)
                        )
                        Text(
                            "Seats: ${BookingManager.selectedSeats.value.joinToString(", ")}",
                            fontSize = 13.sp,
                            color = Color.Gray
                        )
                    } else {
                        Text(
                            "Workspace Reservation",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF5C4033)
                        )
                        Text(
                            BookingManager.workspaceName.value ?: "",
                            fontSize = 13.sp,
                            color = Color.Gray
                        )
                    }
                }
            }

            Text(
                "Reservation Details",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF5C4033)
            )

            InfoItem(
                Icons.Default.CalendarToday,
                "Date",
                BookingManager.selectedDate.value
            )

            InfoItem(
                Icons.Default.Schedule,
                "Time",
                BookingManager.selectedTime.value
            )
        }

        // ---------- CONFIRM BUTTON ----------
        Button(
            onClick = { nav.navigate("booking_success") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(56.dp),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(Color(0xFF5C4033))
        ) {
            Text(
                "Confirm & Reserve",
                fontSize = 17.sp,
                color = Color.White,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun InfoItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF5F5F5), RoundedCornerShape(16.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, null, tint = Color(0xFF5C4033))
        Spacer(Modifier.width(10.dp))
        Column {
            Text(label, fontSize = 12.sp, color = Color.Gray)
            Text(value, fontSize = 15.sp, fontWeight = FontWeight.Medium)
        }
    }
}
