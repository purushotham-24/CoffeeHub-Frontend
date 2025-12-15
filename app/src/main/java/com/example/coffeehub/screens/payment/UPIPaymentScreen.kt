package com.example.coffeehub.screens.payment

import androidx.compose.foundation.background
import androidx.compose.foundation.border       // <-- FIXED
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType

@OptIn(ExperimentalMaterial3Api::class) // warning only, not error
@Composable
fun UPIPaymentScreen(nav: NavController) {

    var selectedApp by remember { mutableStateOf("") }
    var upiId by remember { mutableStateOf("") }

    val upiApps = listOf("Google Pay", "PhonePe", "Paytm")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("UPI Payment", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { nav.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, null, tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF5C4033))
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

            // ₹ Amount Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF5E6CF), RoundedCornerShape(18.dp))
                    .padding(22.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Amount to Pay", color = Color.Gray)
                    Text("₹390", fontSize = 35.sp, fontWeight = FontWeight.Bold, color = Color(0xFF5C4033))
                }
            }

            // UPI App Selection
            Text("Select UPI App", fontWeight = FontWeight.Bold, color = Color(0xFF5C4033))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                upiApps.forEach { app ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(6.dp)
                            .background(
                                if (selectedApp == app) Color(0xFFF5E6CF) else Color.White,
                                RoundedCornerShape(16.dp)
                            )
                            .border(2.dp,                     // <-- NOW VALID
                                if (selectedApp == app) Color(0xFF5C4033) else Color.LightGray,
                                RoundedCornerShape(16.dp)
                            )
                            .padding(vertical = 18.dp)
                            .clickable { selectedApp = app },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(app, color = Color(0xFF5C4033), fontSize = 14.sp)
                    }
                }
            }

            // OR Divider
            Row(verticalAlignment = Alignment.CenterVertically) {
                Divider(Modifier.weight(1f), color = Color.Gray)
                Text(" OR ", color = Color.Gray)
                Divider(Modifier.weight(1f), color = Color.Gray)
            }

            // Manual UPI ID
            Text("Enter UPI ID", fontWeight = FontWeight.Bold, color = Color(0xFF5C4033))

            OutlinedTextField(
                value = upiId,
                onValueChange = { upiId = it },
                placeholder = { Text("yourname@upi") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF5C4033),
                    unfocusedBorderColor = Color.Gray
                )
            )

            Spacer(Modifier.weight(1f))

            // Pay Button
            Button(
                onClick = { nav.navigate("payment_success") },
                enabled = selectedApp.isNotEmpty() || upiId.isNotBlank(),
                modifier = Modifier.fillMaxWidth().height(55.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedApp.isNotEmpty() || upiId.isNotBlank())
                        Color(0xFF5C4033) else Color.LightGray
                )
            ) {
                Text("Pay ₹390", color = Color.White, fontSize = 18.sp)
            }
        }
    }
}
