package com.example.coffeehub.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun HeaderBar(
    title: String = "",
    showBack: Boolean = true,
    showBell: Boolean = false,
    showCart: Boolean = false,
    cartCount: Int = 0,
    nav: NavController
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 14.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            if (showBack) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .background(Color(0xFFF5E6CF), CircleShape)
                        .clickable {
                            if (nav.previousBackStackEntry != null) nav.popBackStack()
                            else nav.navigate("home")
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color(0xFF5C4033)
                    )
                }
                Spacer(Modifier.width(10.dp))
            }

            Text(text = title, fontSize = 20.sp, color = Color.Black)
        }

        Row(verticalAlignment = Alignment.CenterVertically) {

            if (showBell) {
                IconButton(onClick = { nav.navigate("notifications") }) {
                    Icon(
                        Icons.Filled.Notifications,
                        contentDescription = "Notifications",
                        tint = Color(0xFF5C4033)
                    )
                }
            }

            if (showCart) {
                IconButton(
                    onClick = {
                        nav.navigate("cart") {
                            launchSingleTop = true
                            popUpTo("home") { inclusive = false }
                        }
                    }
                ) {
                    Icon(
                        Icons.Filled.ShoppingCart,
                        contentDescription = "Cart",
                        tint = Color(0xFF5C4033)
                    )
                }
            }
        }
    }
}
