package com.example.coffeehub.screens.payment

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.coffeehub.cart.CartManager

data class PaymentMethod(
    val id: String,
    val name: String,
    val description: String,
    val icon: @Composable () -> Unit,
    val path: String
)

@Composable
fun PaymentOptionsScreen(nav: NavController) {

    val brown = Color(0xFF5C4033)

    // 🔥 DYNAMIC TOTAL AMOUNT
    val totalAmount = CartManager.totalAmount

    val paymentMethods = listOf(
        PaymentMethod(
            id = "upi",
            name = "UPI Payment",
            description = "Google Pay, PhonePe, Paytm",
            icon = { Icon(Icons.Filled.Smartphone, contentDescription = null, tint = brown) },
            path = "payment_upi"
        ),
        PaymentMethod(
            id = "card",
            name = "Credit/Debit Card",
            description = "Visa, Mastercard, Rupay",
            icon = { Icon(Icons.Filled.CreditCard, contentDescription = null, tint = brown) },
            path = "payment_card"
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        // 🔝 HEADER
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(brown)
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { nav.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = null, tint = Color.White)
            }
            Spacer(Modifier.width(6.dp))
            Text("Payment Options", color = Color.White, fontSize = 20.sp)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(18.dp)
        ) {

            // 💰 AMOUNT SUMMARY (DYNAMIC)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF5E6CF), RoundedCornerShape(18.dp))
                    .padding(22.dp)
            ) {
                Column {
                    Text("Total Amount", color = Color.Gray, fontSize = 14.sp)
                    Text(
                        text = "₹$totalAmount",
                        color = brown,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(Modifier.height(22.dp))

            Text("Select Payment Method", color = brown, fontSize = 16.sp)
            Spacer(Modifier.height(10.dp))

            // 💳 PAYMENT METHODS
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                paymentMethods.forEach { method ->

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White, RoundedCornerShape(18.dp))
                            .padding(16.dp)
                            .clickable { nav.navigate(method.path) },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .background(Color(0xFFF5E6CF), RoundedCornerShape(14.dp))
                                .padding(10.dp)
                        ) {
                            method.icon()
                        }

                        Spacer(Modifier.width(14.dp))

                        Column(Modifier.weight(1f)) {
                            Text(method.name, color = brown, fontSize = 16.sp)
                            Text(method.description, color = Color.Gray, fontSize = 13.sp)
                        }

                        Icon(
                            Icons.Default.ChevronRight,
                            contentDescription = null,
                            tint = Color.Gray
                        )
                    }
                }
            }

            // 🔐 SECURITY NOTE
            Spacer(Modifier.height(22.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFE8F1FF), RoundedCornerShape(14.dp))
                    .padding(14.dp)
            ) {
                Text(
                    text = "🔒 All payments are secured with SSL encryption",
                    color = Color(0xFF003A8C),
                    fontSize = 13.sp
                )
            }
        }
    }
}
