@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.coffeehub.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NotificationSettingsScreen(onBack: () -> Unit = {}) {

    val notifications = listOf(
        NotificationData("Order Delivered", "Your Cappuccino was delivered", "2 min ago", true, Icons.Filled.CheckCircle),
        NotificationData("New Coffee!", "Try our new Hazelnut Latte", "1 hour ago", true, Icons.Filled.Notifications),
        NotificationData("Booking Reminder", "Workspace starts in 30 mins", "2 hours ago", false, Icons.Filled.Info),
        NotificationData("Special Offer", "Get 20% OFF next order", "1 day ago", false, Icons.Filled.Star)
    )

    Scaffold(topBar = { NotificationHeader(onBack) }) { pad ->
        LazyColumn(
            modifier = Modifier
                .padding(pad)
                .fillMaxSize()
                .background(Color.White)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            items(notifications) { notif ->
                NotificationCard(notif)
            }
        }
    }
}

@Composable
fun NotificationHeader(onBack: () -> Unit) {
    TopAppBar(
        title = { Text("Notifications", fontSize = 20.sp) },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(Icons.Filled.ArrowBack, "")
            }
        }
    )
}

@Composable
fun NotificationCard(n: NotificationData) {

    val bg = if (n.unread) Color(0xFFF5E6CF).copy(alpha = .35f) else Color.White
    val border = if (n.unread) Color(0xFFF5E6CF) else Color.LightGray.copy(alpha = .3f)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(bg)
            .border(1.dp, border, RoundedCornerShape(20.dp))
            .padding(14.dp)
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {

            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFF5E6CF)),
                contentAlignment = Alignment.Center
            ) {
                Icon(n.icon, "", tint = Color(0xFF5C4033), modifier = Modifier.size(26.dp))
            }

            Spacer(Modifier.width(12.dp))

            Column {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(n.title, fontSize = 16.sp)
                    if (n.unread) Box(
                        Modifier.size(9.dp).clip(CircleShape).background(Color(0xFF5C4033))
                    )
                }
                Text(n.message, fontSize = 13.sp, color = Color.DarkGray, modifier = Modifier.padding(vertical = 4.dp))
                Text(n.time, fontSize = 11.sp, color = Color.Gray)
            }
        }
    }
}

data class NotificationData(
    val title: String,
    val message: String,
    val time: String,
    val unread: Boolean,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)