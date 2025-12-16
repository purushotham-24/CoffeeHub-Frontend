package com.example.coffeehub.screens.payment

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.coffeehub.cart.CartManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UPIPaymentScreen(nav: NavController) {

    var selectedApp by remember { mutableStateOf("") }
    var upiId by remember { mutableStateOf("") }

    val upiApps = listOf("Google Pay", "PhonePe", "Paytm")
    val brown = Color(0xFF5C4033)

    // 🔥 DYNAMIC TOTAL AMOUNT
    val totalAmount = CartManager.totalAmount

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("UPI Payment", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { nav.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, null, tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = brown)
            )
        }
    ) { pad ->

        Column(
            modifier = Modifier
                .padding(pad)
                .fillMaxSize()
                .background(Color.White)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            // 💰 Amount Box (DYNAMIC)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF5E6CF), RoundedCornerShape(18.dp))
                    .padding(22.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Amount to Pay", color = Color.Gray)
                    Text(
                        text = "₹$totalAmount",
                        fontSize = 35.sp,
                        fontWeight = FontWeight.Bold,
                        color = brown
                    )
                }
            }

            // 📱 UPI App Selection
            Text("Select UPI App", fontWeight = FontWeight.Bold, color = brown)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                upiApps.forEach { app ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(6.dp)
                            .background(
                                if (selectedApp == app) Color(0xFFF5E6CF) else Color.White,
                                RoundedCornerShape(16.dp)
                            )
                            .border(
                                2.dp,
                                if (selectedApp == app) brown else Color.LightGray,
                                RoundedCornerShape(16.dp)
                            )
                            .padding(vertical = 18.dp)
                            .clickable { selectedApp = app },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(app, color = brown, fontSize = 14.sp)
                    }
                }
            }

            // OR Divider
            Row(verticalAlignment = Alignment.CenterVertically) {
                Divider(Modifier.weight(1f), color = Color.Gray)
                Text(" OR ", color = Color.Gray)
                Divider(Modifier.weight(1f), color = Color.Gray)
            }

            // ✍️ Manual UPI ID
            Text("Enter UPI ID", fontWeight = FontWeight.Bold, color = brown)

            OutlinedTextField(
                value = upiId,
                onValueChange = { upiId = it },
                placeholder = { Text("yourname@upi") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = brown,
                    unfocusedBorderColor = Color.Gray
                )
            )

            Spacer(Modifier.weight(1f))

            // 🔥 Pay Button (DYNAMIC AMOUNT)
            Button(
                onClick = { nav.navigate("payment_success") },
                enabled = selectedApp.isNotEmpty() || upiId.isNotBlank(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor =
                        if (selectedApp.isNotEmpty() || upiId.isNotBlank())
                            brown else Color.LightGray
                )
            ) {
                Text(
                    text = "Pay ₹$totalAmount",
                    color = Color.White,
                    fontSize = 18.sp
                )
            }
        }
    }
}
