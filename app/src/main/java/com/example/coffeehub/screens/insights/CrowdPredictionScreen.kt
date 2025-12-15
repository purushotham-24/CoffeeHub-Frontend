package com.example.coffeehub.screens.prediction
import androidx.compose.foundation.horizontalScroll

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrowdPredictionScreen(navController: NavController) {

    val brown = Color(0xFF5C4033)
    val cream = Color(0xFFF5E6CF)

    val days = listOf("Today", "Tomorrow", "Friday", "Saturday", "Sunday")
    var selectedDay by remember { mutableStateOf("Today") }

    val todayCrowd = listOf(25, 45, 75, 85, 65, 90, 95, 70, 50, 55, 80, 85, 65, 40, 30, 20)

    val hour = java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY)
    val currentLevel = if (hour in 7..22) todayCrowd[hour - 7] else 15

    val levelLabel = when {
        currentLevel >= 90 -> "Peak"
        currentLevel >= 70 -> "High"
        currentLevel >= 40 -> "Medium"
        else -> "Low"
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("CoffeeHub", color = Color.White, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null, tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = brown)
            )
        }
    ) { pad ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(Color.White)
                .padding(pad)
                .padding(16.dp)
        ) {

            // ---------------- CURRENT CROWD CARD ----------------
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(22.dp))
                    .background(
                        Brush.verticalGradient(listOf(brown, Color(0xFF7A5543)))
                    )
                    .padding(22.dp)
            ) {
                Column {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Filled.Groups, contentDescription = null, tint = Color.White)
                            Spacer(Modifier.width(6.dp))
                            Text("Current Status", color = Color.White, fontSize = 14.sp)
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(Color.White.copy(0.25f))
                                .padding(horizontal = 12.dp, vertical = 4.dp)
                        ) {
                            Text(" ", color = Color.White, fontSize = 12.sp)
                            Text(" ", color = Color.White, fontSize = 12.sp)
                        }
                    }

                    Spacer(Modifier.height(18.dp))
                    Text("$currentLevel%", color = Color.White, fontSize = 36.sp, fontWeight = FontWeight.Bold)
                    Text("$levelLabel crowd level", color = cream, fontSize = 15.sp)
                }
            }

            Spacer(Modifier.height(24.dp))

            // ---------------- DAY SELECTOR ----------------
            Row(
                Modifier
                    .horizontalScroll(rememberScrollState())
                    .padding(bottom = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                days.forEach { day ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(30.dp))
                            .background(if (day == selectedDay) brown else cream)
                            .clickable { selectedDay = day }
                            .padding(horizontal = 20.dp, vertical = 8.dp)
                    ) {
                        Text(
                            day,
                            color = if (day == selectedDay) Color.White else brown,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            Spacer(Modifier.height(28.dp))

            // ---------------- PEAK TIMES ----------------
            Text("Peak Times", color = brown, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(12.dp))

            PeakTimeCard("Morning Peak", "9:00 AM - 10:30 AM", 85)
            Spacer(Modifier.height(10.dp))
            PeakTimeCard("Lunch Peak", "12:00 PM - 1:30 PM", 95)
            Spacer(Modifier.height(10.dp))
            PeakTimeCard("Evening Peak", "5:00 PM - 6:30 PM", 83)

            Spacer(Modifier.height(30.dp))

            // ---------------- BEST TIMES ----------------
            Text("Best Times to Visit", color = brown, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(12.dp))

            BestTimeCard("Early Morning", "7:00 AM - 8:30 AM", 30)
            Spacer(Modifier.height(10.dp))
            BestTimeCard("Mid Afternoon", "2:30 PM - 4:30 PM", 52)
            Spacer(Modifier.height(10.dp))
            BestTimeCard("Late Evening", "8:00 PM - 10:00 PM", 28)

            Spacer(Modifier.height(40.dp))
        }
    }
}

@Composable
fun PeakTimeCard(title: String, time: String, level: Int) {
    val barColor = when {
        level >= 90 -> Color.Red
        level >= 70 -> Color(0xFFF97316)
        else -> Color.Yellow
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .border(1.dp, Color.LightGray, RoundedCornerShape(18.dp))
            .padding(16.dp)
    ) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(title, color = Color(0xFF5C4033), fontSize = 17.sp, fontWeight = FontWeight.Bold)
            Text("$level% full", color = Color.Red, fontSize = 12.sp)
        }

        Spacer(Modifier.height(6.dp))
        Text(time, color = Color.Gray, fontSize = 13.sp)

        Spacer(Modifier.height(12.dp))

        Box(
            Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.LightGray)
        ) {
            Box(
                Modifier
                    .fillMaxHeight()
                    .width((level * 2).dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(barColor)
            )
        }
    }
}

@Composable
fun BestTimeCard(title: String, time: String, level: Int) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .border(1.dp, Color(0xFF90EE90), RoundedCornerShape(18.dp))
            .background(Color(0xFFEAFEEA))
            .padding(16.dp)
    ) {
        Text(title, color = Color(0xFF5C4033), fontSize = 17.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(6.dp))
        Text(time, color = Color.Gray, fontSize = 13.sp)
        Spacer(Modifier.height(8.dp))
        Text("$level% full • Less crowded", color = Color(0xFF2E8B57), fontSize = 13.sp)
    }
}
