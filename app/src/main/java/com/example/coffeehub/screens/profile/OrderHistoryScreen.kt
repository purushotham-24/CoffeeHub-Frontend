package com.example.coffeehub.screens.orders

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip        //← FIXED
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderHistoryScreen(nav: NavController) {

    val brown = Color(0xFF5C4033)

    val orders = listOf(
        OrderItem("#ORD123456", "Dec 15, 2024", "10:30 AM", 3, 390, "Completed"),
        OrderItem("#ORD123455", "Dec 14, 2024", "2:15 PM", 2, 280, "Completed"),
        OrderItem("#ORD123454", "Dec 13, 2024", "11:00 AM", 1, 150, "Completed"),
        OrderItem("#ORD123453", "Dec 12, 2024", "3:45 PM", 4, 520, "Cancelled"),
        OrderItem("#ORD123452", "Dec 11, 2024", "9:30 AM", 2, 260, "Completed"),
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Order History", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { nav.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null, tint = Color.White)
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
                .padding(14.dp)
        ) {

            orders.forEach { order ->
                OrderCard(order = order) {
                    nav.navigate("tracking")   // <– edit later when tracking screen added
                }
                Spacer(Modifier.height(10.dp))
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
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(order.id, fontSize = 17.sp, color = Color(0xFF5C4033))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.AccessTime, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(15.dp))
                    Spacer(Modifier.width(4.dp))
                    Text("${order.date} • ${order.time}", fontSize = 13.sp, color = Color.Gray)
                }
                Text("${order.items} items", fontSize = 13.sp, color = Color.Gray)
                Text("₹${order.total}", fontSize = 17.sp, color = Color(0xFF5C4033))
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(order.status, fontSize = 14.sp,
                    color = if (order.status == "Cancelled") Color.Red else Color(0xFF0A8F52))
                Spacer(Modifier.height(6.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("View Details", color = Color(0xFF5C4033))
                    Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Color(0xFF5C4033))
                }
            }
        }
    }
}

data class OrderItem(
    val id: String,
    val date: String,
    val time: String,
    val items: Int,
    val total: Int,
    val status: String
)
