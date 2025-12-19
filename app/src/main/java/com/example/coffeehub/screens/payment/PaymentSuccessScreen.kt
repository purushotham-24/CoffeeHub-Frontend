package com.example.coffeehub.screens.payment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.coffeehub.cart.CartManager
import com.example.coffeehub.data.network.RetrofitClient
import com.example.coffeehub.screens.orders.OrderHistoryManager
import com.example.coffeehub.screens.tracking.OrderTrackingManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun PaymentSuccessScreen(nav: NavController) {

    val brown = Color(0xFF5C4033)
    val cream = Color(0xFFF5E6CF)
    val successGreen = Color(0xFF21A44A)

    val totalAmount = CartManager.totalAmount
    val orderId = remember { "#ORD${System.currentTimeMillis()}" }
    val scope = rememberCoroutineScope()

    fun saveOrder() {
        if (totalAmount <= 0) return

        // UI
        OrderHistoryManager.addOrder(
            id = orderId,
            items = 1,
            total = totalAmount
        )

        // Backend
        scope.launch(Dispatchers.IO) {
            try {
                RetrofitClient.api.placeOrder(
                    mapOf(
                        "user_id" to 1,
                        "order_id" to orderId,
                        "items" to 1,
                        "total" to totalAmount,
                        "status" to "Completed"
                    )
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(22.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(Modifier.height(18.dp))

        Box(
            modifier = Modifier
                .size(110.dp)
                .background(Color(0xFFDEF7DA), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.CheckCircle, null, tint = successGreen, modifier = Modifier.size(80.dp))
        }

        Spacer(Modifier.height(20.dp))

        Text("Payment Successful!", color = brown, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Text("Your payment has been processed successfully", color = Color.Gray, fontSize = 13.sp)

        Spacer(Modifier.height(26.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(cream, RoundedCornerShape(20.dp))
                .padding(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text("Amount Paid", color = Color.Gray)
            Text("₹$totalAmount", color = brown, fontSize = 30.sp, fontWeight = FontWeight.Bold)

            Spacer(Modifier.height(12.dp))
            HorizontalDivider()
            Spacer(Modifier.height(12.dp))

            PaymentInfoRow("Transaction ID", orderId)
            PaymentInfoRow("Payment Method", "UPI")
            PaymentInfoRow("Status", "Success", Color(0xFF2E7D32))
        }

        Spacer(Modifier.height(30.dp))

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {

            OutlinedButton(
                modifier = Modifier.weight(1f),
                onClick = {
                    saveOrder()
                    CartManager.clear()
                    nav.navigate("home") { popUpTo("payment") { inclusive = true } }
                }
            ) { Text("Home") }

            Button(
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = brown),
                onClick = {
                    saveOrder()
                    CartManager.clear()
                    OrderTrackingManager.reset()
                    OrderTrackingManager.startTracking()
                    nav.navigate("tracking") { popUpTo("payment") { inclusive = true } }
                }
            ) { Text("Track Order", color = Color.White) }
        }
    }
}

@Composable
fun PaymentInfoRow(label: String, value: String, color: Color = Color(0xFF5C4033)) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, color = Color.Gray, fontSize = 12.sp)
        Text(value, color = color, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
    }
}