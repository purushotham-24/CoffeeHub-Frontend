package com.simats.coffeehub.screens.booking

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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

    val name = when (id) {
        "1" -> "Solo Workspace"
        "2" -> "Duo Workspace"
        "3" -> "Team Workspace"
        else -> "Workspace"
    }

    // ✅ ENSURE WORKSPACE STATE
    BookingManager.bookingType.value = "workspace"
    BookingManager.workspaceId.value = id
    BookingManager.workspaceName.value = name

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9F2E7))
    ) {

        // ---------- TOP BAR ----------
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(brown)
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { nav.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, null, tint = Color.White)
            }
            Spacer(Modifier.width(8.dp))
            Text(
                text = name,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        // ---------- CONTENT ----------
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(22.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text(
                "Perfect workspace for focus & productivity",
                color = Color.Gray
            )

            Divider()

            FeatureChip("⚡ High-Speed WiFi")
            FeatureChip("☕ Unlimited Coffee")
            FeatureChip("❄ Air Conditioned")
            FeatureChip("🔌 Power Backup")

            Spacer(Modifier.height(10.dp))

            Button(
                onClick = { nav.navigate("workspace_pricing/$id") },
                modifier = Modifier.fillMaxWidth().height(54.dp),
                colors = ButtonDefaults.buttonColors(brown),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text("View Pricing", color = Color.White)
            }

            OutlinedButton(
                onClick = { nav.navigate("datetime") },
                modifier = Modifier.fillMaxWidth().height(54.dp),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text("Book Now", color = brown)
            }
        }
    }
}

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
            .padding(12.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(text, fontWeight = FontWeight.Medium, color = Color(0xFF5C4033))
    }
}
