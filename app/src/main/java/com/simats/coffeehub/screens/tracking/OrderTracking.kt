
@file:OptIn(ExperimentalMaterial3Api::class)

package com.simats.coffeehub.screens.tracking

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun OrderTrackingScreen(nav: NavController) {

    val brown = Color(0xFF5C4033)
    val green = Color(0xFF2E7D32)
    val lightGrey = Color(0xFFF6F6F6)

    val orderSteps = OrderTrackingManager.steps

    // 🔥 Start timer once screen opens
    LaunchedEffect(Unit) {
        OrderTrackingManager.startTracking()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Order Tracking",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            nav.navigate("home") {
                                popUpTo("home") { inclusive = true }
                            }
                        }
                    ) {
                        Icon(
                            imageVector = androidx.compose.material.icons.Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = brown,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color.White)
                .verticalScroll(rememberScrollState())
        ) {

            /* ---------------- TIMELINE CARD ---------------- */
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 14.dp)
                    .background(lightGrey, RoundedCornerShape(20.dp))
                    .padding(vertical = 22.dp, horizontal = 18.dp)
            ) {

                orderSteps.forEachIndexed { index, step ->

                    Row(
                        verticalAlignment = Alignment.Top,
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {

                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(CircleShape)
                                    .background(
                                        when {
                                            step.completed -> green
                                            step.active -> brown
                                            else -> Color.LightGray
                                        }
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = step.id.toString(),
                                    color = Color.White,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            if (index < orderSteps.lastIndex) {
                                Box(
                                    modifier = Modifier
                                        .width(3.dp)
                                        .height(52.dp)
                                        .background(
                                            if (step.completed) green else Color.LightGray
                                        )
                                )
                            }
                        }

                        Spacer(Modifier.width(16.dp))

                        Column(modifier = Modifier.padding(top = 2.dp)) {

                            Text(
                                text = step.name,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = when {
                                    step.completed -> green
                                    step.active -> brown
                                    else -> Color.Gray
                                }
                            )

                            Spacer(Modifier.height(4.dp))

                            Text(
                                text = when {
                                    step.active -> "In progress"
                                    step.completed -> "Completed"
                                    else -> "Pending"
                                },
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        }
                    }

                    if (index != orderSteps.lastIndex) {
                        Spacer(Modifier.height(20.dp))
                    }
                }
            }

            /* ---------------- MESSAGE + TIMER BELOW SERVED ---------------- */
            val seconds = OrderTrackingManager.remainingSeconds.value
            val minutes = seconds / 60
            val secs = seconds % 60

            Spacer(Modifier.height(12.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "☕ Your coffee is being prepared",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.DarkGray,
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = String.format(
                        "It will be served in %02d:%02d mins",
                        minutes,
                        secs
                    ),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = brown
                )
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}