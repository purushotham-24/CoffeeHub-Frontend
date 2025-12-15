package com.example.coffeehub.screens.booking

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.coffeehub.screens.home.components.HeaderBar  // your header component
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check

@Composable
fun SeatLayoutMap(nav: NavController) {

    var selectedSeat by remember { mutableStateOf<String?>(null) }

    val seats = listOf(
        "A1" to "available", "A2" to "available", "A3" to "occupied", "A4" to "available",
        "B1" to "available", "B2" to "occupied", "B3" to "available", "B4" to "available",
        "C1" to "occupied", "C2" to "available", "C3" to "available", "C4" to "occupied",
        "D1" to "available", "D2" to "available", "D3" to "available", "D4" to "available"
    )

    Column(
        Modifier.fillMaxSize().background(Color.White)
    ) {

        // Top bar
        HeaderBar(title = "Select Your Seat", showBack = true, nav = nav)

        Column(Modifier.padding(20.dp)) {

            // Entrance indicator just like React UI
            Box(
                Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF5E6CF), RoundedCornerShape(20.dp))
                    .padding(14.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("Entrance", color = Color(0xFF5C4033), fontSize = 16.sp)
            }

            Spacer(Modifier.height(22.dp))

            // 4 column seat grid (same as grid in React)
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                seats.chunked(4).forEach { row ->
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        row.forEach { (seatID, status) ->
                            val isSelected = selectedSeat == seatID
                            val isOccupied = status == "occupied"

                            Box(
                                modifier = Modifier
                                    .size(70.dp)
                                    .background(
                                        when {
                                            isOccupied -> Color(0xFFE5E5E5) // disabled look
                                            isSelected -> Color(0xFF5C4033)
                                            else -> Color(0xFFF5E6CF)
                                        },
                                        RoundedCornerShape(16.dp)
                                    )
                                    .clickable(enabled = !isOccupied) { selectedSeat = seatID },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    seatID,
                                    color = if (isSelected) Color.White else if (isOccupied) Color.Gray else Color(0xFF5C4033),
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

            // Legend (Available / Selected / Occupied)
            Row(
                Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF7F7F7), RoundedCornerShape(20.dp))
                    .padding(14.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                LegendDot(Color(0xFFF5E6CF), "Available")
                LegendDot(Color(0xFF5C4033), "Selected", textColor = Color.White)
                LegendDot(Color(0xFFE5E5E5), "Occupied")
            }
        }

        Spacer(Modifier.weight(1f))

        // Bottom button (same UX as continue button in React)
        Button(
            onClick = { nav.navigate("datetime") },  // NEXT screen
            enabled = selectedSeat != null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .height(58.dp),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(
                if (selectedSeat != null) Color(0xFF5C4033) else Color.LightGray
            )
        ) {
            Text(
                text = selectedSeat?.let { "Confirm Seat $it" } ?: "Select a Seat",
                fontSize = 17.sp,
                color = if (selectedSeat != null) Color.White else Color.Gray,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun LegendDot(color: Color, text: String, textColor: Color = Color.Gray) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(Modifier.size(22.dp).background(color, shape = CircleShape))
        Spacer(Modifier.width(8.dp))
        Text(text, fontSize = 13.sp, color = textColor)
    }
}
