package com.example.coffeehub.screens.bookings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

import androidx.compose.foundation.clickable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeatBookingDetailsScreen(nav: NavController) {

    val brown = Color(0xFF5C4033)
    val filterItems = listOf("All", "Upcoming", "Completed")
    var selected by remember { mutableStateOf("All") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Bookings", color = Color.White, fontSize = 20.sp) },
                navigationIcon = {
                    IconButton(onClick = { nav.navigate("profile") }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = brown)
            )
        }
    ) { pad ->

        Column(
            modifier = Modifier
                .padding(pad)
                .fillMaxSize()
                .background(Color.White)
        ) {

            // 🔥 FILTER BAR
            Row(
                Modifier.fillMaxWidth().padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                filterItems.forEach { item ->
                    Text(
                        item,
                        modifier = Modifier
                            .weight(1f)   // 🔥 FIXED HERE
                            .padding(6.dp)
                            .background(
                                if (selected == item) brown else Color.LightGray,
                                RoundedCornerShape(10.dp)
                            )
                            .padding(vertical = 10.dp)
                            .clickable { selected = item },
                        color = if (selected == item) Color.White else Color.DarkGray,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        fontSize = 15.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Content (Demo Only)
            Text(
                "Booking List Appears Here...",
                modifier = Modifier.fillMaxWidth().padding(20.dp),
                fontSize = 18.sp,
                color = brown
            )
        }
    }
}
