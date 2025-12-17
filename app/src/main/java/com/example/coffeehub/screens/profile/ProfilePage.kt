package com.example.coffeehub.screens.profile

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfilePage(nav: NavController) {

    val context = nav.context
    val prefs = context.getSharedPreferences("coffeehub_prefs", Context.MODE_PRIVATE)

    val name = prefs.getString("profile_name", "") ?: ""
    val email = prefs.getString("profile_email", "") ?: ""
    val phone = prefs.getString("profile_phone", "") ?: ""

    val initials = name
        .trim()
        .split(" ")
        .filter { it.isNotEmpty() }
        .take(2)
        .joinToString("") { it.first().uppercaseChar().toString() }
        .ifEmpty { "U" }

    val brown = Color(0xFF5C4033)
    val cream = Color(0xFFF5E6CF)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Profile",
                        fontSize = 20.sp,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { nav.popBackStack() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = brown
                )
            )
        }
    ) { pad ->

        Column(
            modifier = Modifier
                .padding(pad)
                .fillMaxSize()
                .background(Color.White)
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(cream)
                    .padding(20.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {

                    Box(
                        modifier = Modifier
                            .size(75.dp)
                            .clip(CircleShape)
                            .background(brown),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = initials,
                            color = Color.White,
                            fontSize = 28.sp
                        )
                    }

                    Spacer(Modifier.width(16.dp))

                    Column {
                        Text(
                            text = if (name.isNotBlank()) name else "User",
                            fontSize = 20.sp,
                            color = brown
                        )
                        Text(
                            text = if (email.isNotBlank()) email else "—",
                            color = Color.Gray,
                            fontSize = 13.sp
                        )
                        Text(
                            text = if (phone.isNotBlank()) phone else "—",
                            color = Color.Gray,
                            fontSize = 13.sp
                        )
                    }
                }
            }

            Spacer(Modifier.height(18.dp))

            MenuItem("Edit Profile", Icons.Default.Person) {
                nav.navigate("edit-profile")
            }

            MenuItem("Order History", Icons.Default.History) {
                nav.navigate("order-history")
            }

            MenuItem("Seat Bookings", Icons.Default.EventSeat) {
                nav.navigate("booking-details")
            }

            MenuItem("Help & Support", Icons.Default.Help) {
                nav.navigate("support")
            }

            Spacer(Modifier.height(20.dp))

            MenuItemRed("Logout", Icons.Default.Logout) {
                nav.navigate("login") {
                    popUpTo("home") { inclusive = true }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "CoffeeHub v1.0.0",
                color = Color.Gray,
                fontSize = 12.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(10.dp)
            )
        }
    }
}

@Composable
fun MenuItem(
    text: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(14.dp))
            .clickable(onClick = onClick)
            .background(Color.White)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = Color(0xFF5C4033))
        Spacer(Modifier.width(14.dp))
        Text(
            text = text,
            fontSize = 16.sp,
            color = Color(0xFF5C4033),
            modifier = Modifier.weight(1f)
        )
        Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Color.Gray)
    }
}

@Composable
fun MenuItemRed(
    text: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(14.dp))
            .clickable(onClick = onClick)
            .background(Color(0xFFFFEBEE))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = Color.Red)
        Spacer(Modifier.width(14.dp))
        Text(
            text = text,
            fontSize = 16.sp,
            color = Color.Red,
            modifier = Modifier.weight(1f)
        )
        Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Color.Gray)
    }
}
