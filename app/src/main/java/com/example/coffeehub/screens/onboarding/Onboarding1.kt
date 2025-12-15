package com.example.coffeehub.screens.onboarding

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun Onboarding1(nav: NavController) {

    val coffeeBrown = Color(0xFF4A3528)
    val coffeeGold  = Color(0xFFD7B894)
    val cream       = Color(0xFFF8EADB)

    val floatAnim by rememberInfiniteTransition().animateFloat(
        initialValue = -6f,
        targetValue = 6f,
        animationSpec = infiniteRepeatable(
            tween(1800), RepeatMode.Reverse
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(cream, coffeeBrown)))
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(Modifier.height(80.dp))

        Box(
            modifier = Modifier
                .offset(y = floatAnim.dp)
                .size(150.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.75f)),
            contentAlignment = Alignment.Center
        ) {
            Text("☕", fontSize = 60.sp)
        }

        Spacer(Modifier.height(30.dp))

        Text("Coffee Hub", fontSize = 40.sp, fontWeight = FontWeight.ExtraBold, color = coffeeGold)
        Text("Premium Coffee • Workspace • Relaxation", fontSize = 16.sp, textAlign = TextAlign.Center, color = cream.copy(0.9f))

        Spacer(Modifier.height(120.dp))

        Button(
            onClick = {
                nav.navigate("login") {
                    popUpTo("features") { inclusive = true }  // 🔥 splash removed from back
                }
            },
            modifier = Modifier.fillMaxWidth().height(60.dp),
            colors = ButtonDefaults.buttonColors(coffeeGold),
            shape = CircleShape
        ) {
            Text("Get Started", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = coffeeBrown)
        }
    }
}
