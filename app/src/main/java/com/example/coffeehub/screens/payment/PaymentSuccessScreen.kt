package com.example.coffeehub.screens.payment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.coffeehub.cart.CartManager
import com.example.coffeehub.screens.tracking.OrderTrackingManager
import com.example.coffeehub.screens.orders.OrderHistoryManager

@Composable
fun PaymentSuccessScreen(nav: NavController) {

    val brown = Color(0xFF5C4033)
    val cream = Color(0xFFF5E6CF)
    val successGreen = Color(0xFF21A44A)

    val amount = "₹${CartManager.totalAmount}"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 22.dp, vertical = 26.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(Modifier.height(18.dp))

        Box(
            modifier = Modifier
                .size(110.dp)
                .background(Color(0xFFDEF7DA), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.CheckCircle,
                contentDescription = null,
                tint = successGreen,
                modifier = Modifier.size(80.dp)
            )
        }

        Spacer(Modifier.height(20.dp))

        Text(
            text = "Payment Successful!",
            color = brown,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(6.dp))

        Text(
            text = "Your payment has been processed successfully",
            color = Color.Gray,
            fontSize = 13.sp,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(26.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(cream, RoundedCornerShape(20.dp))
                .padding(vertical = 22.dp, horizontal = 18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text("Amount Paid", color = Color.Gray, fontSize = 12.sp)
            Spacer(Modifier.height(4.dp))

            Text(
                text = amount,
                color = brown,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(16.dp))
            Divider()
            Spacer(Modifier.height(12.dp))

            PaymentInfoRow("Transaction ID", "#TXN123456")
            Spacer(Modifier.height(6.dp))
            PaymentInfoRow("Payment Method", "UPI")
            Spacer(Modifier.height(6.dp))
            PaymentInfoRow("Status", "Success", Color(0xFF2E7D32))
        }

        Spacer(Modifier.height(30.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // 🔹 HOME
            OutlinedButton(
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                border = ButtonDefaults.outlinedButtonBorder,
                onClick = {

                    // ✅ SAVE ORDER (SAFE)
                    OrderHistoryManager.addOrder(
                        id = "#ORD${System.currentTimeMillis().toString().takeLast(6)}",
                        items = 0,
                        total = CartManager.totalAmount
                    )

                    CartManager.clear()
                    nav.navigate("home") {
                        popUpTo("payment") { inclusive = true }
                    }
                }
            ) {
                Text("Home", color = brown, fontSize = 12.sp, fontWeight = FontWeight.Medium)
            }

            // 🔹 TRACK ORDER
            Button(
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = brown),
                onClick = {

                    // ✅ SAVE ORDER (SAFE)
                    OrderHistoryManager.addOrder(
                        id = "#ORD${System.currentTimeMillis().toString().takeLast(6)}",
                        items = 0,
                        total = CartManager.totalAmount
                    )

                    CartManager.clear()
                    OrderTrackingManager.reset()
                    OrderTrackingManager.startTracking()

                    nav.navigate("tracking") {
                        popUpTo("payment") { inclusive = true }
                    }
                }
            ) {
                Text("Track Order", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Medium)
            }
        }

        Spacer(Modifier.height(10.dp))
    }
}

@Composable
fun PaymentInfoRow(
    label: String,
    value: String,
    color: Color = Color(0xFF5C4033)
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, color = Color.Gray, fontSize = 12.sp)
        Text(value, color = color, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
    }
}
