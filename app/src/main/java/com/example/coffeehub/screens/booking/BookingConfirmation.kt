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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        //---------------- TOP BAR ----------------//
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF5C4033))
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier
                    .size(26.dp)
                    .clickable { nav.popBackStack() }
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = "Booking Confirmation",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        //---------------- DETAILS ----------------//
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // Seat card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(Color(0xFFF5E6CF))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Seat Reservation Confirm", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF5C4033))
                    Text("Selected Seat: D3", fontSize = 13.sp, color = Color(0xFF5C4033).copy(alpha = .7f))
                }
            }

            Text("Reservation Details", color = Color(0xFF5C4033), fontSize = 18.sp, fontWeight = FontWeight.Bold)

            InfoItem(Icons.Default.CalendarToday, "Date", "Dec 15, 2024")
            InfoItem(Icons.Default.Schedule, "Time", "10:00 AM - 2:00 PM")
            InfoItem(Icons.Default.Schedule, "Payment Method", "UPI")

            // PRICE SUMMARY
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(Color(0xFFF8F8F8))
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Price Summary", fontSize = 17.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF5C4033))

                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Base Price", color = Color.Gray)
                        Text("₹400", color = Color(0xFF5C4033))
                    }

                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Taxes & Fees", color = Color.Gray)
                        Text("₹50", color = Color(0xFF5C4033))
                    }

                    Divider()

                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Total Amount", fontWeight = FontWeight.Bold, color = Color(0xFF5C4033))
                        Text("₹450", fontWeight = FontWeight.Bold, color = Color(0xFF5C4033))
                    }
                }
            }
        }

        //---------------- CONFIRM & RESERVE BUTTON (WORKING) ----------------//
        Button(
            onClick = { nav.navigate("booking_success") },         //  FIXED
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFF5C4033)),
            shape = RoundedCornerShape(50)
        ) {
            Text("Confirm & Reserve", fontSize = 17.sp, color = Color.White, fontWeight = FontWeight.SemiBold)
        }
    }
}


//---------- REUSABLE ROW ----------
@Composable
fun InfoItem(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF5F5F5), RoundedCornerShape(16.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, null, tint = Color(0xFF5C4033), modifier = Modifier.size(22.dp))
        Spacer(Modifier.width(10.dp))
        Column {
            Text(label, fontSize = 12.sp, color = Color.Gray)
            Text(value, fontSize = 15.sp, fontWeight = FontWeight.Medium, color = Color(0xFF5C4033))
        }
    }
}
