package com.example.coffeehub.screens.booking

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.coffeehub.screens.home.components.HeaderBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateTimeSelection(nav: NavController) {

    var selectedDate by remember { mutableStateOf("2024-11-20") }
    var selectedTime by remember { mutableStateOf("") }

    val timeSlots = listOf(
        "09:00 AM","10:00 AM","11:00 AM","12:00 PM",
        "01:00 PM","02:00 PM","03:00 PM","04:00 PM","05:00 PM","06:00 PM"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        HeaderBar(
            title = "Select Date & Time",
            nav = nav,
            showBack = true
        )

        Column(Modifier.padding(20.dp)) {

            // DATE
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.CalendarToday, null, tint = Color(0xFF5C4033))
                Spacer(Modifier.width(8.dp))
                Text("Select Date", fontWeight = FontWeight.SemiBold)
            }

            Spacer(Modifier.height(10.dp))

            OutlinedTextField(
                value = selectedDate,
                onValueChange = { selectedDate = it },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp)
            )

            Spacer(Modifier.height(24.dp))

            // TIME
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Schedule, null, tint = Color(0xFF5C4033))
                Spacer(Modifier.width(8.dp))
                Text("Select Time", fontWeight = FontWeight.SemiBold)
            }

            Spacer(Modifier.height(12.dp))

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                timeSlots.chunked(3).forEach { row ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        row.forEach { time ->
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(48.dp)
                                    .background(
                                        if (selectedTime == time)
                                            Color(0xFF5C4033)
                                        else
                                            Color(0xFFF5E6CF),
                                        RoundedCornerShape(14.dp)
                                    )
                                    .clickable { selectedTime = time },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    time,
                                    color = if (selectedTime == time)
                                        Color.White
                                    else
                                        Color(0xFF5C4033),
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(Modifier.weight(1f))

        // CONTINUE BUTTON
        Button(
            onClick = {

                // SAVE DATE & TIME
                BookingManager.selectedDate.value = selectedDate
                BookingManager.selectedTime.value = selectedTime

                // 🔥 IMPORTANT FIX FOR WORKSPACE FLOW
                if (BookingManager.bookingType.value.isBlank()) {
                    BookingManager.bookingType.value = "workspace"
                }

                nav.navigate("booking_confirmation")
            },
            enabled = selectedTime.isNotEmpty(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .height(56.dp),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(Color(0xFF5C4033))
        ) {
            Text(
                "Continue",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}
