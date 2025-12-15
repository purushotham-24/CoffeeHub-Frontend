package com.example.coffeehub.screens.locations

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

// ================= DATA CLASS =================
data class LocationData(
    val id: String,
    val name: String,
    val address: String,
    val distance: String,
    val phone: String,
    val hours: String,
    val rating: Double,
    val available: Int
)

// ================= MAIN SCREEN =================
@Composable
fun NearbyLocationsScreen(nav: NavController) {

    var view by remember { mutableStateOf("list") }

    val brown = Color(0xFF5C4033)
    val cream = Color(0xFFF5E6CF)

    Column(Modifier.fillMaxSize().background(Color.White)) {

        // ================= HEADER =================
        Column(
            Modifier
                .background(brown)
                .padding(22.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.ArrowBack, "", tint = Color.White,
                    modifier = Modifier.size(26.dp).clickable { nav.popBackStack() })

                Spacer(Modifier.width(12.dp))

                Column {
                    Text("Nearby Locations", color = Color.White, fontSize = 20.sp)
                    Text("Find Coffee Hub near you", color = cream, fontSize = 13.sp)
                }
            }

            Spacer(Modifier.height(14.dp))

            // 🔥 View Switch Buttons (weight FIXED)
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                ModeChip(
                    text = "List View",
                    selected = view == "list",
                    onClick = { view = "list" },
                    modifier = Modifier.weight(1f)
                )

                ModeChip(
                    text = "Map View",
                    selected = view == "map",
                    onClick = { view = "map" },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Spacer(Modifier.height(14.dp))

        // ================= LIST VIEW =================
        if (view == "list") {
            val locations = listOf(
                LocationData("1","Coffee Hub - MG Road","MG Road, Bangalore","0.5 km","+91 98765 43210","7AM - 11PM",4.8,12),
                LocationData("2","Coffee Hub - Indiranagar","100ft Road, Bangalore","2.3 km","+91 98765 43211","7AM - 11PM",4.6,8),
                LocationData("3","Coffee Hub - Koramangala","5th Block, Bangalore","3.8 km","+91 98765 43212","7AM - 12AM",4.7,15),
                LocationData("4","Coffee Hub - Whitefield","ITPL Road, Bangalore","8.5 km","+91 98765 43213","6:30AM - 11PM",4.5,20),
            )

            Column(
                Modifier.verticalScroll(rememberScrollState()).padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                locations.forEach { LocationCard(it) }
            }
        }

        // ================= MAP VIEW =================
        else {
            Box(
                Modifier.fillMaxSize().background(
                    Brush.verticalGradient(listOf(cream, Color.White))
                ),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.LocationOn, "", tint = brown, modifier = Modifier.size(90.dp))
                    Text("Map Coming Soon", color = brown, fontSize = 18.sp)
                    Text("Google Map integration next update", color = Color.Gray, fontSize = 12.sp)
                }
            }
        }
    }
}

// ================= CHIP COMPONENT =================
@Composable
fun ModeChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val brown = Color(0xFF5C4033)
    Box(
        modifier
            .clip(RoundedCornerShape(50.dp))
            .background(if (selected) Color.White else Color.White.copy(.25f))
            .clickable { onClick() }
            .padding(vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text, color = if (selected) brown else Color.White, fontSize = 14.sp)
    }
}

// ================= LOCATION CARD =================
@Composable
fun LocationCard(l: LocationData) {

    val brown = Color(0xFF5C4033)

    Column(
        Modifier.fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(Color.White)
            .padding(16.dp)
    ) {

        // Rating Badge
        Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.TopEnd) {
            Box(
                Modifier.clip(RoundedCornerShape(50.dp))
                    .background(Color.Yellow.copy(.35f))
                    .padding(horizontal = 10.dp, vertical = 5.dp)
            ) { Text("⭐ ${l.rating}", color = brown, fontSize = 13.sp) }
        }

        Text(l.name, fontSize = 18.sp, color = brown)
        Text(l.address, color = Color.Gray, fontSize = 12.sp)
        Text("${l.distance} away", color = Color.Gray, fontSize = 12.sp)
        Text("📞 ${l.phone}", color = brown, fontSize = 13.sp)
        Text("⏳ ${l.hours}", color = brown, fontSize = 13.sp)

        Spacer(Modifier.height(12.dp))

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(Modifier.size(8.dp).clip(CircleShape).background(Color.Green))
                Spacer(Modifier.width(6.dp))
                Text("${l.available} seats available", fontSize = 12.sp, color = Color.Gray)
            }

            Button(colors = ButtonDefaults.buttonColors(brown), onClick = { }) {
                Text("Visit", color = Color.White)
            }
        }
    }
}
