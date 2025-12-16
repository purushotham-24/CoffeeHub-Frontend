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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.coffeehub.cart.CartManager   // ✅ ADD

@Composable
fun PaymentSuccessScreen(nav: NavController) {

    val brown = Color(0xFF5C4033)
    val cream = Color(0xFFF5E6CF)

    // 🔥 DYNAMIC AMOUNT
    val amount = "₹${CartManager.totalAmount}"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(22.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // 🎉 Success Icon
        Box(
            modifier = Modifier
                .size(120.dp)
                .background(Color(0xFFDEF7DA), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.CheckCircle,
                contentDescription = "Success",
                tint = Color(0xFF21A44A),
                modifier = Modifier.size(90.dp)
            )
        }

        Spacer(Modifier.height(18.dp))

        Text(
            "Payment Successful!",
            color = brown,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            "Your payment has been processed successfully",
            color = Color.Gray,
            fontSize = 14.sp
        )

        Spacer(Modifier.height(28.dp))

        // 💳 Payment Summary Card
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(cream, RoundedCornerShape(22.dp))
                .padding(22.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Amount Paid", color = Color.Gray, fontSize = 14.sp)
            Text(amount, color = brown, fontSize = 32.sp, fontWeight = FontWeight.Bold)

            Spacer(Modifier.height(18.dp))
            Divider(color = brown.copy(alpha = 0.3f))

            Spacer(Modifier.height(14.dp))

            PaymentInfoRow("Transaction ID", "#TXN123456789")
            PaymentInfoRow("Date & Time", "Dec 15, 2024 10:30 AM")
            PaymentInfoRow("Payment Method", "UPI")
            PaymentInfoRow("Status", "Success", valueColor = Color(0xFF22A645))
        }

        Spacer(Modifier.height(30.dp))

        // 🔘 Buttons
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            onClick = {
                // ✅ CLEAR CART AFTER SUCCESS
                CartManager.clear()
                nav.navigate("tracking")
            },
            colors = ButtonDefaults.buttonColors(containerColor = brown)
        ) {
            Text("Track Your Order", color = Color.White, fontSize = 16.sp)
        }

        Spacer(Modifier.height(10.dp))

        TextButton(
            onClick = {
                CartManager.clear()
                nav.navigate("home")
            }
        ) {
            Text(
                "Back to Home",
                color = brown,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun PaymentInfoRow(
    label: String,
    value: String,
    valueColor: Color = Color(0xFF5C4033)
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = Color.Gray, fontSize = 14.sp)
        Text(value, color = valueColor, fontSize = 14.sp, fontWeight = FontWeight.Bold)
    }
}
