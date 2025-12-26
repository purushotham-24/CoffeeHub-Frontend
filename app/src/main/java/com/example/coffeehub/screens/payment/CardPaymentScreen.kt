package com.example.coffeehub.screens.payment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.coffeehub.cart.CartManager   // ✅ ADD THIS

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardPaymentScreen(nav: NavController) {

    // 🔥 DYNAMIC TOTAL AMOUNT
    val totalAmount = CartManager.totalAmount

    var cardNumber by remember { mutableStateOf("") }
    var cardName by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }
    var saveCard by remember { mutableStateOf(false) }

    // Auto MM/YY format
    fun handleExpiry(text: String) {
        var value = text.filter { it.isDigit() }
        if (value.length > 2)
            value = value.substring(0, 2) + "/" +
                    value.substring(2, minOf(4, value.length))
        if (value.length <= 5) expiryDate = value
    }

    val brown = Color(0xFF5C4033)
    val cream = Color(0xFFF5E6CF)

    val isValid =
        cardNumber.length == 16 &&
                cardName.isNotBlank() &&
                expiryDate.length == 5 &&
                cvv.length == 3

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Card Payment", fontSize = 20.sp, color = Color.White) },
                navigationIcon = {
                    IconButton({ nav.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, null, tint = Color.White)
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
                .verticalScroll(rememberScrollState())
                .background(Color.White)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(22.dp)
        ) {

            // 💰 Billing Summary (DYNAMIC)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(6.dp, RoundedCornerShape(22.dp))
                    .background(cream, RoundedCornerShape(22.dp))
                    .padding(28.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Amount to Pay", color = Color.Gray, fontSize = 14.sp)
                    Text(
                        text = "₹$totalAmount",
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold,
                        color = brown
                    )
                }
            }

            // 🟤 Live Card Preview (UNCHANGED)
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .shadow(10.dp, RoundedCornerShape(25.dp))
                    .background(
                        Brush.linearGradient(listOf(brown, Color(0xFF3E2C22))),
                        RoundedCornerShape(25.dp)
                    )
                    .padding(22.dp)
            ) {
                Column(verticalArrangement = Arrangement.SpaceBetween) {

                    Text("💳", fontSize = 36.sp, color = Color.White)

                    Text(
                        text = if (cardNumber.isEmpty()) "**** **** **** ****"
                        else cardNumber.chunked(4).joinToString(" "),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("Card Holder", color = Color.White.copy(.6f), fontSize = 12.sp)
                            Text(
                                if (cardName.isEmpty()) "YOUR NAME" else cardName,
                                color = Color.White
                            )
                        }
                        Column {
                            Text("Expires", color = Color.White.copy(.6f), fontSize = 12.sp)
                            Text(
                                if (expiryDate.isEmpty()) "MM/YY" else expiryDate,
                                color = Color.White
                            )
                        }
                    }
                }
            }

            // 🔢 Input Fields (UNCHANGED)
            OutlinedTextField(
                value = cardNumber,
                onValueChange = { if (it.length <= 16) cardNumber = it },
                label = { Text("Card Number") },
                placeholder = { Text("1234567890123456") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp)
            )

            OutlinedTextField(
                value = cardName,
                onValueChange = { cardName = it.uppercase() },
                label = { Text("Card Holder Name") },
                placeholder = { Text("JOHN DOE") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp)
            )

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {

                OutlinedTextField(
                    value = expiryDate,
                    onValueChange = { handleExpiry(it) },
                    label = { Text("Expiry") },
                    placeholder = { Text("MM/YY") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(14.dp)
                )

                OutlinedTextField(
                    value = cvv,
                    onValueChange = { if (it.length <= 3) cvv = it },
                    label = { Text("CVV") },
                    placeholder = { Text("123") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(14.dp)
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = saveCard, onCheckedChange = { saveCard = it })
                Text("Save card for future", fontSize = 14.sp, color = Color.Gray)
            }

            // 🔥 Payment Button (DYNAMIC)
            Button(
                onClick = { if (isValid) nav.navigate("payment_success") },
                enabled = isValid,
                modifier = Modifier.fillMaxWidth().height(58.dp),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(if (isValid) brown else Color.LightGray)
            ) {
                Text(
                    text = "Pay ₹$totalAmount",
                    fontSize = 18.sp,
                    color = Color.White
                )
            }
        }
    }
}