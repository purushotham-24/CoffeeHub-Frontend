package com.simats.coffeehub.screens.orders

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
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
fun OrderHistoryScreen(nav: NavController) {

    val brown = Color(0xFF5C4033)
    val orders = OrderHistoryManager.orders

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Order History", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { nav.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, null, tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = brown)
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color.White)
                .padding(14.dp)
        ) {

            if (orders.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No orders yet", color = Color.Gray, fontSize = 16.sp)
                }
            } else {
                orders.forEach { order ->
                    OrderCard(order) {
                        nav.navigate("tracking")
                    }
                    Spacer(Modifier.height(10.dp))
                }
            }
        }
    }
}

@Composable
fun OrderCard(order: OrderItem, onClick: () -> Unit) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .border(1.dp, Color.LightGray, RoundedCornerShape(16.dp))
            .padding(14.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Column {
                Text(order.id, fontSize = 17.sp, color = Color(0xFF5C4033))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.AccessTime,
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.size(15.dp)
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        "${order.date} • ${order.time}",
                        fontSize = 13.sp,
                        color = Color.Gray
                    )
                }

                Text("${order.items} items", fontSize = 13.sp, color = Color.Gray)
                Text("₹${order.total}", fontSize = 17.sp, color = Color(0xFF5C4033))
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(order.status, fontSize = 14.sp, color = Color(0xFF0A8F52))
                Spacer(Modifier.height(6.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("View Details", color = Color(0xFF5C4033))
                    Icon(Icons.Default.ChevronRight, null, tint = Color(0xFF5C4033))
                }
            }
        }
    }
}
