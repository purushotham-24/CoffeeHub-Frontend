package com.example.coffeehub.screens.booking

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun WorkspaceDetails(nav: NavController, id: String) {

    val brown = Color(0xFF5C4033)
    val cream = Color(0xFFF9F2E7)

    // Workspace Title
    val name = when(id) {
        "1" -> "Solo Workspace"
        "2" -> "Duo Workspace"
        "3" -> "Team Workspace"
        else -> "Workspace"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(cream)
            .padding(22.dp),
        verticalArrangement = Arrangement.spacedBy(22.dp)
    ) {

        //---------------- Header ----------------//
        Text(
            name,
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold,
            color = brown
        )

        Text(
            "A peaceful, productive work spot with comfort and fast connectivity.",
            fontSize = 15.sp,
            color = Color.Gray
        )

        Divider(thickness = 1.dp, color = Color(0xFFD6C7B5))

        //---------------- Workspace Features 🔥 ----------------//
        Text("What you get 🚀", fontSize = 17.sp, fontWeight = FontWeight.SemiBold, color = brown)

        FeatureChip("⚡ High-Speed WiFi")
        FeatureChip("🔌 Charging Points & Power Backup")
        FeatureChip("☕ Unlimited Hot Coffee")
        FeatureChip("🪑 Premium Comfortable Seating")
        FeatureChip("❄ Air Conditioned Environment")
        FeatureChip("📝 Perfect for Focus, Coding & Study")

        Spacer(modifier = Modifier.height(14.dp))

        //---------------- Buttons ----------------//
        Button(
            onClick = { nav.navigate("workspace_pricing/$id") }, // SAME NAVIGATION
            modifier = Modifier.fillMaxWidth().height(54.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(brown)
        ) {
            Text("View Pricing", fontSize = 18.sp, color = Color.White)
        }

        OutlinedButton(
            onClick = { nav.navigate("datetime") }, // SAME FLOW
            modifier = Modifier.fillMaxWidth().height(54.dp),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text("Book Now", fontSize = 18.sp, color = brown)
        }
    }
}

//---------- Reusable Feature Chip UI ----------//
@Composable
fun FeatureChip(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.linearGradient(
                    listOf(Color(0xFFFFF4E5), Color(0xFFFFEAD2))
                ),
                RoundedCornerShape(14.dp)
            )
            .padding(vertical = 10.dp, horizontal = 14.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(text, fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color(0xFF5C4033))
    }
}
