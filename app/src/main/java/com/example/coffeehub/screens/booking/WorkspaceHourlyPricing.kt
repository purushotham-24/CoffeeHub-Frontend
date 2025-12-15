package com.example.coffeehub.screens.booking

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun WorkspaceHourlyPricing(nav: NavController, id: String) {

    val brown = Color(0xFF5C4033)
    val cream = Color(0xFFF7EFE4)

    val price = when(id) {
        "1" -> "₹199/hour"
        "2" -> "₹399/hour"
        "3" -> "₹599/hour"
        else -> "₹299/hour"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(cream)
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(22.dp)
    ) {

        //---------------- HEADER ----------------//
        Text("Pricing Details", fontSize = 26.sp, fontWeight = FontWeight.ExtraBold, color = brown)
        Text("Workspace ID: $id", fontSize = 14.sp, color = Color.Gray)

        //---------------- PRICE BOX 🔥 ----------------//
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.horizontalGradient(listOf(Color(0xFFFFEED2), Color(0xFFFFE1BA))),
                    RoundedCornerShape(16.dp)
                )
                .padding(vertical = 18.dp),
            contentAlignment = androidx.compose.ui.Alignment.Center
        ) {
            Text(
                price,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = brown
            )
        }

        //---------------- BENEFITS DISPLAY ----------------//
        Text("You Get:", fontWeight = FontWeight.SemiBold, fontSize = 16.sp, color = brown)

        PriceFeature("⏳ Flexible hourly booking")
        PriceFeature("⚡ Ultra-fast WiFi access")
        PriceFeature("🔌 Desk charging ports included")
        PriceFeature("☕ Unlimited coffee access")
        PriceFeature("❄ Calm & Air-conditioned workspace")

        Spacer(Modifier.height(10.dp))

        //---------------- BUTTON ----------------//
        Button(
            onClick = { nav.navigate("datetime") }, // SAME ROUTE ✔
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(brown)
        ) { Text("Continue to Book →", color = Color.White, fontSize = 18.sp) }
    }
}


//------------ REUSABLE ROW FOR FEATURES ------------//
@Composable
fun PriceFeature(text: String) {
    Text(text, fontSize = 14.sp, color = Color(0xFF5C4033), modifier = Modifier.padding(start = 6.dp))
}
