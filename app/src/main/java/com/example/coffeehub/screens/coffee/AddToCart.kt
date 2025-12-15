package com.example.coffeehub.screens.coffee

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun AddToCart(navController: NavController) {

    val Brown = Color(0xFF5C4033)
    val GreenLight = Color(0xFFD1FADF)
    val Green = Color(0xFF22C55E)

    Box(
        Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f)),
        contentAlignment = Alignment.Center
    ) {

        Column(
            Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .background(Color.White, RoundedCornerShape(24.dp))
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Green success icon
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(GreenLight, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "",
                    tint = Green,
                    modifier = Modifier.size(50.dp)
                )
            }

            Spacer(Modifier.height(16.dp))

            Text("Added to Cart!", fontSize = 22.sp, color = Color.Black)
            Text(
                "Your item has been added to the cart",
                color = Color.Gray,
                fontSize = 14.sp
            )

            Spacer(Modifier.height(24.dp))

            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

                // View Cart
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(Brown, RoundedCornerShape(50.dp))
                        .clickable {
                            navController.navigate("cart") {
                                popUpTo("add_to_cart") { inclusive = true }
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text("View Cart", color = Color.White)
                }

                // Continue Shopping → Home
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .border(1.dp, Brown, RoundedCornerShape(50.dp))
                        .clickable {
                            navController.navigate("home") {
                                popUpTo("add_to_cart") { inclusive = true }
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text("Continue Shopping", color = Brown)
                }
            }
        }
    }
}
