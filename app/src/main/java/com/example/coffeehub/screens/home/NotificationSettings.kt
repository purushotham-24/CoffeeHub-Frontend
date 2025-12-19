@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.coffeehub.screens.home

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coffeehub.data.model.AppNotification
import com.example.coffeehub.data.repository.NotificationRepository
import kotlinx.coroutines.launch

@Composable
fun NotificationSettings(onBack: () -> Unit = {}) {

    val context = LocalContext.current
    val prefs = context.getSharedPreferences("coffeehub_prefs", Context.MODE_PRIVATE)
    val userId = prefs.getInt("user_id", 0)

    val repo = remember { NotificationRepository() }
    val scope = rememberCoroutineScope()

    var notifications by remember { mutableStateOf<List<AppNotification>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }

    val brown = Color(0xFF5C4033)

    /* ---------- LOAD FROM BACKEND ---------- */
    LaunchedEffect(Unit) {
        if (userId != 0) {
            notifications = repo.getNotifications(userId)
        }
        loading = false
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Notifications",
                        fontSize = 20.sp,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    if (notifications.isNotEmpty()) {
                        TextButton(
                            onClick = {
                                scope.launch {
                                    if (repo.clearAll(userId)) {
                                        notifications = emptyList()
                                    }
                                }
                            }
                        ) {
                            Text(
                                "Clear All",
                                color = Color.White,
                                fontSize = 14.sp
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = brown
                )
            )
        }
    ) { padding ->

        when {
            loading -> {
                Box(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = brown)
                }
            }

            notifications.isEmpty() -> {
                Box(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize()
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No notifications yet",
                        color = Color.Gray,
                        fontSize = 16.sp
                    )
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier
                        .padding(padding)
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
    }
}

@Composable
fun NotificationCard(n: AppNotification) {

    val brown = Color(0xFF5C4033)

    val bgColor =
        if (n.unread) Color(0xFFF5E6CF).copy(alpha = 0.35f)
        else Color.White

    val borderColor =
        if (n.unread) Color(0xFFF5E6CF)
        else Color.LightGray.copy(alpha = 0.3f)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(bgColor)
            .border(1.dp, borderColor, RoundedCornerShape(20.dp))
            .padding(14.dp)
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {

            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFF5E6CF)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    n.icon,
                    contentDescription = null,
                    tint = brown
                )
            }

            Spacer(Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    n.title,
                    fontSize = 16.sp
                )

                Spacer(Modifier.height(2.dp))

                Text(
                    n.message,
                    fontSize = 13.sp,
                    color = Color.DarkGray
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    n.time,
                    fontSize = 11.sp,
                    color = Color.Gray
                )
            }
        }
    }
}
