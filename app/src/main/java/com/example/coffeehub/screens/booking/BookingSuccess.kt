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

    Column(
        Modifier
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
                .background(Color(0xFFB7E9B0), shape = CircleShape),
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
            "Your workspace has been successfully reserved",
            color = Color.Gray,
            fontSize = 14.sp
        )

        Spacer(Modifier.height(26.dp))

        // BOOKING CARD
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(Color(0xFFF5E6CF)),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {

                Text("Booking Details", fontSize = 17.sp, color = Color(0xFF5C4033))

                DetailRow(Icons.Default.LocationOn, "Location", "Premium Workspace - 2nd Floor")
                DetailRow(Icons.Default.CalendarMonth, "Date", "Dec 15, 2024")
                DetailRow(Icons.Default.Schedule, "Time", "10:00 AM - 2:00 PM")

                Divider(color = Color(0xFF5C4033).copy(alpha = 0.2f))

                Text("Booking ID", fontSize = 12.sp, color = Color.Gray)
                Text("#BK123456789", color = Color(0xFF5C4033), fontWeight = FontWeight.Bold)
            }
        }

        Spacer(Modifier.height(24.dp))

        // 🔥 Only button remaining — Go to Home
        Button(
            onClick = { nav.navigate("home") },
            modifier = Modifier.fillMaxWidth().height(55.dp),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(Color(0xFF5C4033))
        ) {
            Text("Back to Home", color = Color.White, fontSize = 17.sp)
        }
    }
}

@Composable
fun DetailRow(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, value: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, null, tint = Color(0xFF5C4033), modifier = Modifier.size(22.dp))
        Spacer(Modifier.width(10.dp))
        Column {
            Text(label, fontSize = 12.sp, color = Color.Gray)
            Text(value, color = Color(0xFF5C4033), fontWeight = FontWeight.Medium)
        }
    }
}
