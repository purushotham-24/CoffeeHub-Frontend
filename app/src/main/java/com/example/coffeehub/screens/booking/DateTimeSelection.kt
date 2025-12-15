package com.example.coffeehub.screens.booking

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Schedule

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateTimeSelection(nav: NavController) {

    var selectedDate by remember { mutableStateOf("2024-11-20") }
    var selectedTime by remember { mutableStateOf("") }

    val timeSlots = listOf(
        "09:00 AM","10:00 AM","11:00 AM","12:00 PM","01:00 PM",
        "02:00 PM","03:00 PM","04:00 PM","05:00 PM","06:00 PM"
    )

    Column(Modifier.fillMaxSize().background(Color.White)) {

        HeaderBar(title = "Select Date & Time", nav = nav, showBack = true)

        Column(Modifier.padding(20.dp)) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.CalendarToday, contentDescription = null, tint = Color(0xFF5C4033))
                Spacer(Modifier.width(8.dp))
                Text("Select Date", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            }

            Spacer(Modifier.height(10.dp))

            // **FIXED COLOR SECTION**
            OutlinedTextField(
                value = selectedDate,
                onValueChange = { selectedDate = it },
                modifier = Modifier.fillMaxWidth().height(55.dp),
                shape = RoundedCornerShape(18.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF5C4033),
                    unfocusedBorderColor = Color(0xFFF5E6CF),
                    focusedLabelColor = Color(0xFF5C4033)
                )
            )

            Spacer(Modifier.height(28.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.Schedule, contentDescription = null, tint = Color(0xFF5C4033))
                Spacer(Modifier.width(8.dp))
                Text("Select Time", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            }

            Spacer(Modifier.height(12.dp))

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                timeSlots.chunked(3).forEach { row ->
                    Row(Modifier.fillMaxWidth(), Arrangement.spacedBy(10.dp)) {
                        row.forEach { time ->
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(48.dp)
                                    .background(
                                        if (selectedTime == time) Color(0xFF5C4033)
                                        else Color(0xFFF5E6CF).copy(alpha = 0.6f),
                                        RoundedCornerShape(14.dp)
                                    )
                                    .clickable { selectedTime = time },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    time,
                                    fontSize = 13.sp,
                                    color = if (selectedTime == time) Color.White else Color(0xFF5C4033),
                                    fontWeight = if (selectedTime == time) FontWeight.Bold else FontWeight.Medium
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(Modifier.weight(1f))

        Button(
            onClick = { nav.navigate("booking_confirmation") },
            enabled = selectedTime.isNotEmpty(),
            modifier = Modifier.fillMaxWidth().padding(20.dp).height(60.dp),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (selectedTime.isNotEmpty()) Color(0xFF5C4033) else Color.LightGray
            )
        ) {
            Text("Continue", fontSize = 17.sp,
                color = if (selectedTime.isNotEmpty()) Color.White else Color.Gray)
        }
    }
}
