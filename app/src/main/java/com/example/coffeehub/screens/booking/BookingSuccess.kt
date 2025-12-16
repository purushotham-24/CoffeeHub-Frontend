package com.example.coffeehub.screens.booking

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.LocationOn
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
fun BookingSuccess(nav: NavController) {

    val isSeatBooking = BookingManager.bookingType.value == "seat"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(22.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // SUCCESS ICON
        Box(
            Modifier
                .size(95.dp)
                .background(Color(0xFFB7E9B0), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.CheckCircle,
                contentDescription = null,
                tint = Color(0xFF2E7D32),
                modifier = Modifier.size(60.dp)
            )
        }

        Spacer(Modifier.height(18.dp))

        Text("Booking Confirmed!", fontSize = 22.sp, color = Color(0xFF5C4033))
        Text(
            "Your reservation was successful",
            color = Color.Gray,
            fontSize = 14.sp
        )

        Spacer(Modifier.height(26.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(Color(0xFFF5E6CF)),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(
                Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {

                Text("Booking Details", fontSize = 17.sp, color = Color(0xFF5C4033))

                if (isSeatBooking) {
                    DetailRow(
                        Icons.Default.LocationOn,
                        "Seats",
                        BookingManager.selectedSeats.value.joinToString(", ")
                    )
                } else {
                    DetailRow(
                        Icons.Default.LocationOn,
                        "Workspace",
                        BookingManager.workspaceName.value ?: ""
                    )
                }

                DetailRow(
                    Icons.Default.CalendarMonth,
                    "Date",
                    BookingManager.selectedDate.value
                )

                DetailRow(
                    Icons.Default.Schedule,
                    "Time",
                    BookingManager.selectedTime.value
                )
            }
        }

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = {
                BookingManager.clear()
                nav.navigate("home") {
                    popUpTo("home") { inclusive = true }
                }
            },
            modifier = Modifier.fillMaxWidth().height(55.dp),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(Color(0xFF5C4033))
        ) {
            Text("Back to Home", color = Color.White, fontSize = 17.sp)
        }
    }
}

@Composable
fun DetailRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, null, tint = Color(0xFF5C4033))
        Spacer(Modifier.width(10.dp))
        Column {
            Text(label, fontSize = 12.sp, color = Color.Gray)
            Text(value, fontWeight = FontWeight.Medium, color = Color(0xFF5C4033))
        }
    }
}
