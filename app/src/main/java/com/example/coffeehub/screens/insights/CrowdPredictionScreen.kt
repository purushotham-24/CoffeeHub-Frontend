package com.example.coffeehub.screens.prediction

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoGraph
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.coffeehub.data.network.CrowdRetrofit
import com.example.coffeehub.model.CrowdResponse
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrowdPredictionScreen(nav: NavController) {

    val brown = Color(0xFF5C4033)
    val bg = Color(0xFFF8F6F4)
    val light = Color(0xFFF3ECE7)

    var data by remember { mutableStateOf<CrowdResponse?>(null) }
    var selectedDay by remember { mutableStateOf("Today") }
    var loading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        data = CrowdRetrofit.api.getCrowdData()
        loading = false
    }

    val ist = TimeZone.getTimeZone("Asia/Kolkata")
    fun formatTime(hour: Int): String {
        val cal = Calendar.getInstance(ist)
        cal.set(Calendar.HOUR_OF_DAY, hour)
        cal.set(Calendar.MINUTE, 0)
        return SimpleDateFormat("hh:mm a", Locale.getDefault()).format(cal.time)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("AI Crowd Insights", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { nav.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, null, tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = brown)
            )
        }
    ) { padding ->

        if (loading || data == null) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = brown)
            }
            return@Scaffold
        }

        val dayData = when (selectedDay) {
            "Tomorrow" -> data!!.Tomorrow
            "Friday" -> data!!.Friday
            "Saturday" -> data!!.Saturday
            "Sunday" -> data!!.Sunday
            else -> data!!.Today
        }

        val indexed = dayData.mapIndexed { i, v -> i to v }
        val best = indexed.minBy { it.second }
        val peak = indexed.maxBy { it.second }

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(bg)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {

            // AI HERO CARD
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = brown)
            ) {
                Column(Modifier.padding(20.dp)) {
                    Icon(Icons.Default.AutoGraph, null, tint = Color.White)
                    Spacer(Modifier.height(8.dp))
                    Text("AI Recommendation", color = Color.White, fontSize = 14.sp)
                    Text(
                        formatTime(7 + best.first),
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "Best time to visit • ${best.second}% crowd",
                        color = Color.White.copy(0.9f),
                        fontSize = 13.sp
                    )
                }
            }

            Spacer(Modifier.height(20.dp))

            // DAY SELECTOR
            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                listOf("Today","Tomorrow","Friday","Saturday","Sunday").forEach { day ->
                    Box(
                        modifier = Modifier
                            .background(
                                if (day == selectedDay) brown else light,
                                RoundedCornerShape(20.dp)
                            )
                            .clickable { selectedDay = day }
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(
                            day,
                            color = if (day == selectedDay) Color.White else brown,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            // PEAK & BEST
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp)
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text("Today Summary", fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(8.dp))
                    Text("🟢 Best Time : ${formatTime(7 + best.first)} (${best.second}%)")
                    Text("🔴 Peak Time : ${formatTime(7 + peak.first)} (${peak.second}%)")
                }
            }

            Spacer(Modifier.height(24.dp))

            // HOURLY GRAPH
            Text("Hourly Crowd Forecast", fontWeight = FontWeight.Bold, color = brown)
            Spacer(Modifier.height(10.dp))

            indexed.forEach { (i, value) ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        formatTime(7 + i),
                        modifier = Modifier.width(70.dp),
                        fontSize = 12.sp
                    )
                    Box(
                        modifier = Modifier
                            .height(8.dp)
                            .width((value * 2).dp)
                            .background(
                                when {
                                    value >= 70 -> Color.Red
                                    value >= 40 -> Color(0xFFFF9800)
                                    else -> Color(0xFF4CAF50)
                                },
                                RoundedCornerShape(6.dp)
                            )
                    )
                    Spacer(Modifier.width(8.dp))
                    Text("$value%", fontSize = 12.sp)
                }
                Spacer(Modifier.height(6.dp))
            }

            Spacer(Modifier.height(26.dp))

            // AI FOOTER
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp)
            ) {
                Row(Modifier.padding(16.dp)) {
                    Icon(Icons.Default.Groups, null, tint = brown)
                    Spacer(Modifier.width(10.dp))
                    Text(
                        "AI predicts crowd behaviour using trained historical data. "
                                + "This helps users avoid peak hours and choose the best visiting time.",
                        fontSize = 13.sp,
                        color = Color.DarkGray
                    )
                }
            }

            Spacer(Modifier.height(30.dp))
        }
    }
}
