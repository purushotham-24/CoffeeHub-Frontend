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

    var selectedSeats by remember { mutableStateOf(setOf<String>()) }
    val seats = SeatManager.seats

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        HeaderBar(
            title = "Select Your Seats",
            showBack = true,
            nav = nav
        )

        Column(modifier = Modifier.padding(20.dp)) {

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

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                seats.chunked(4).forEach { row ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        row.forEach { seat ->

                            val isOccupied = seat.status == "occupied"
                            val isSelected = selectedSeats.contains(seat.id)

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
                                                selectedSeats - seat.id
                                            else
                                                selectedSeats + seat.id
                                    },
                                contentAlignment = Alignment.Center
                            ) {

                                Text(
                                    seat.id,
                                    color = when {
                                        isOccupied -> Color.Gray
                                        isSelected -> Color.White
                                        else -> Color(0xFF5C4033)
                                    },
                                    fontWeight = FontWeight.SemiBold
                                )

                                if (isSelected) {
                                    Icon(
                                        Icons.Default.Check,
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

        Button(
            onClick = {
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
            colors = ButtonDefaults.buttonColors(Color(0xFF5C4033))
        ) {
            Text(
                "Confirm Seats (${selectedSeats.joinToString(", ")})",
                fontSize = 16.sp,
                color = Color.White,
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
