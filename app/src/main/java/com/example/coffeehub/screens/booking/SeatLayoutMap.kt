package com.example.coffeehub.screens.booking

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
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

@Composable
fun SeatLayoutMap(nav: NavController) {

    // ✅ LOCAL UI STATE
    var selectedSeats by remember { mutableStateOf(setOf<String>()) }

    // 🪑 STATIC SEAT MAP (can come from backend later)
    val seats = listOf(
        "A1" to "available", "A2" to "available", "A3" to "occupied", "A4" to "available",
        "B1" to "available", "B2" to "occupied", "B3" to "available", "B4" to "available",
        "C1" to "occupied", "C2" to "available", "C3" to "available", "C4" to "occupied",
        "D1" to "available", "D2" to "available", "D3" to "available", "D4" to "available"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        // 🔝 HEADER
        HeaderBar(title = "Select Your Seats", showBack = true, nav = nav)

        Column(Modifier.padding(20.dp)) {

            // 🚪 Entrance
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF5E6CF), RoundedCornerShape(20.dp))
                    .padding(14.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("Entrance", color = Color(0xFF5C4033), fontSize = 16.sp)
            }

            Spacer(Modifier.height(22.dp))

            // 🪑 SEAT GRID
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                seats.chunked(4).forEach { row ->
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        row.forEach { (seatId, status) ->

                            val isOccupied = status == "occupied"
                            val isSelected = selectedSeats.contains(seatId)

                            Box(
                                modifier = Modifier
                                    .size(70.dp)
                                    .background(
                                        when {
                                            isOccupied -> Color(0xFFE5E5E5)
                                            isSelected -> Color(0xFF5C4033)
                                            else -> Color(0xFFF5E6CF)
                                        },
                                        RoundedCornerShape(16.dp)
                                    )
                                    .clickable(enabled = !isOccupied) {
                                        selectedSeats =
                                            if (isSelected)
                                                selectedSeats - seatId
                                            else
                                                selectedSeats + seatId
                                    },
                                contentAlignment = Alignment.Center
                            ) {

                                Text(
                                    seatId,
                                    color = when {
                                        isOccupied -> Color.Gray
                                        isSelected -> Color.White
                                        else -> Color(0xFF5C4033)
                                    },
                                    fontWeight = FontWeight.SemiBold
                                )

                                if (isSelected) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier
                                            .align(Alignment.TopEnd)
                                            .padding(5.dp)
                                            .size(16.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(30.dp))

            // 🟢 LEGEND
            Row(
                Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF7F7F7), RoundedCornerShape(20.dp))
                    .padding(14.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                LegendDot(Color(0xFFF5E6CF), "Available")
                LegendDot(Color(0xFF5C4033), "Selected", Color.White)
                LegendDot(Color(0xFFE5E5E5), "Occupied")
            }
        }

        Spacer(Modifier.weight(1f))

        // 🔘 CONFIRM BUTTON
        Button(
            onClick = {
                // ✅ SAVE INTO GLOBAL MANAGER
                BookingManager.bookingType.value = "seat"
                BookingManager.selectedSeats.value = selectedSeats.toList()

                nav.navigate("datetime")
            },
            enabled = selectedSeats.isNotEmpty(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .height(58.dp),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (selectedSeats.isNotEmpty())
                    Color(0xFF5C4033) else Color.LightGray
            )
        ) {
            Text(
                text = if (selectedSeats.isNotEmpty())
                    "Confirm Seats (${selectedSeats.joinToString(", ")})"
                else
                    "Select Seats",
                fontSize = 16.sp,
                color = if (selectedSeats.isNotEmpty()) Color.White else Color.Gray,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun LegendDot(color: Color, text: String, textColor: Color = Color.Gray) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(22.dp)
                .background(color, CircleShape)
        )
        Spacer(Modifier.width(8.dp))
        Text(text, fontSize = 13.sp, color = textColor)
    }
}
