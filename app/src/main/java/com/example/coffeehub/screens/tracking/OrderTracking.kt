package com.example.coffeehub.screens.tracking

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape   // <-- IMPORTANT
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun OrderTrackingScreen(nav: NavController) {

    val brown = Color(0xFF5C4033)
    val cream = Color(0xFFF5E6CF)

    val orderSteps = listOf(
        StepModel(1,"Order Received", completed = true, active = false),
        StepModel(2,"Order Confirmed", completed = true, active = false),
        StepModel(3,"Preparing", completed = false, active = true),
        StepModel(4,"Ready to Serve", completed = false, active = false),
        StepModel(5,"Served", completed = false, active = false)
    )

    Scaffold { pad ->
        Column(
            Modifier.fillMaxSize()
                .padding(pad)
                .background(Color.White)
                .verticalScroll(rememberScrollState())
        ) {

            // 🔹 Header
            Row(
                Modifier.fillMaxWidth()
                    .background(brown)
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.ArrowBack,
                    tint = Color.White,
                    contentDescription = null,
                    modifier = Modifier.size(26.dp).clickable { nav.popBackStack() }
                )
                Spacer(Modifier.width(12.dp))
                Text("Order Tracking", color = Color.White, fontSize = 20.sp)
            }

            // 🔹 Order Card
            Box(
                Modifier.fillMaxWidth().padding(16.dp)
                    .clip(RoundedCornerShape(18.dp))                // FIXED
                    .background(cream)
                    .padding(14.dp)
            ) {
                Column {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Column {
                            Text("Order ID", fontSize = 12.sp, color = Color.Gray)
                            Text("#ORD123456", color = brown, fontSize = 18.sp)
                        }
                        Box(
                            Modifier.clip(RoundedCornerShape(50.dp)) // FIXED
                                .background(Color(0xFFFFDEB3))
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text("In Progress", color = Color(0xFFA46A00), fontSize = 13.sp)
                        }
                    }

                    Spacer(Modifier.height(10.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Phone,"", tint=brown)
                        Spacer(Modifier.width(8.dp))
                        Text("Estimated time: 15 mins", color=brown)
                    }
                }
            }

            // 🔹 Timeline Steps
            Column(Modifier.padding(16.dp)) {
                orderSteps.forEachIndexed { index, step ->
                    Row {

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {

                            Box(
                                Modifier.size(42.dp).clip(RoundedCornerShape(50.dp))   // FIXED
                                    .background(
                                        when {
                                            step.completed -> Color.Green
                                            step.active -> brown
                                            else -> Color.LightGray
                                        }
                                    ),
                                contentAlignment = Alignment.Center
                            ) { Text("${step.id}", color = Color.White) }

                            if (index < orderSteps.size - 1)
                                Box(
                                    Modifier.width(3.dp).height(55.dp)
                                        .background(if (step.completed) Color.Green else Color.LightGray)
                                )
                        }

                        Column(Modifier.padding(start = 14.dp)) {
                            Text(
                                step.name,
                                fontWeight = FontWeight.SemiBold,
                                color = when {
                                    step.active || step.completed -> brown
                                    else -> Color.Gray
                                }
                            )
                            if (step.active)
                                Text("Your order is being prepared",color=Color.Gray)

                            if (step.completed)
                                Text("Completed ✓", color = Color(0xFF2E7D32))
                        }
                    }
                    Spacer(Modifier.height(16.dp))
                }
            }

            // 🔹 Support
            Box(
                Modifier.fillMaxWidth().padding(16.dp)
                    .clip(RoundedCornerShape(18.dp))  // FIXED
                    .background(Color(0xFFF6F2EA))
                    .padding(14.dp)
            ) {
                Row {
                    Icon(Icons.Default.Phone,"", tint=brown)
                    Spacer(Modifier.width(10.dp))
                    Column {
                        Text("Contact Support", color=brown)
                        Text("+91 98765 43210", color=Color.Gray)
                    }
                }
            }

            // 🔹 Item List
            Box(
                Modifier.fillMaxWidth().padding(16.dp)
                    .clip(RoundedCornerShape(18.dp))  // FIXED
                    .background(cream)
                    .padding(14.dp)
            ) {
                Column {
                    Text("Order Items", color=brown)
                    Spacer(Modifier.height(10.dp))

                    OrderItemRow("2x Cappuccino (Medium)",240)
                    OrderItemRow("1x Cold Coffee (Large)",150)
                }
            }

            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                colors = ButtonDefaults.buttonColors(brown),
                shape = RoundedCornerShape(35.dp)   // FIXED
            ) { Text("Call Support", color = Color.White) }

            Spacer(Modifier.height(30.dp))
        }
    }
}


// REUSABLE
@Composable
fun OrderItemRow(name:String, amount:Int){
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(name, color=Color.Gray)
        Text("₹$amount", color=Color(0xFF5C4033), fontWeight=FontWeight.Bold)
    }
}

data class StepModel(val id:Int,val name:String,val completed:Boolean,val active:Boolean)
