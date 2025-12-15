package com.example.coffeehub.screens.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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

//-------------------------------------------------------------
// DATA MODEL
//-------------------------------------------------------------
data class PromoCodeData(
    val code: String,
    val discount: String,
    val description: String,
    val minOrder: Int
)

//-------------------------------------------------------------
// PROMO SCREEN (Polished UI)
//-------------------------------------------------------------
@Composable
fun Promo(nav: NavController) {

    var enteredCode by remember { mutableStateOf("") }
    var selectedCode by remember { mutableStateOf<String?>(null) }

    val promoList = listOf(
        PromoCodeData("COFFEE20", "20% OFF", "Get 20% off on your order", 200),
        PromoCodeData("FIRST50", "₹50 OFF", "First order discount", 150),
        PromoCodeData("WEEKEND30", "30% OFF", "Weekend special offer", 300),
        PromoCodeData("FREESHIP", "Free Service", "Free table service on all orders", 0)
    )

    Column(Modifier.fillMaxSize().background(Color.White)) {

        //---------------------------------------------------------
        // HEADER WITH POLISHED BACK BUTTON
        //---------------------------------------------------------
        Row(
            Modifier
                .fillMaxWidth()
                .background(Color(0xFF5C4033))
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // ⭐ Premium Back Button
            IconButton(
                onClick = {
                    if (nav.previousBackStackEntry != null) nav.popBackStack()
                    else nav.navigate("cart")
                },
                modifier = Modifier
                    .size(42.dp)
                    .background(Color.White.copy(alpha = 0.15f), shape = RoundedCornerShape(50))
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier.size(26.dp)
                )
            }

            Spacer(Modifier.width(12.dp))

            Text(
                "Promo Code",
                fontSize = 20.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        //---------------------------------------------------------
        // BODY
        //---------------------------------------------------------
        Column(
            Modifier
                .padding(16.dp)
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {

            // INPUT BOX
            Text("Enter Promo Code", color = Color(0xFF5C4033))
            Spacer(Modifier.height(8.dp))

            Row {

                TextField(
                    value = enteredCode,
                    onValueChange = { enteredCode = it.uppercase() },
                    placeholder = { Text("Enter code") },
                    singleLine = true,
                    modifier = Modifier
                        .weight(1f)
                        .border(2.dp, Color.LightGray, RoundedCornerShape(12.dp)),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )

                Spacer(Modifier.width(10.dp))

                Button(
                    onClick = { if (enteredCode.isNotEmpty()) selectedCode = enteredCode },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5C4033)),
                    modifier = Modifier.height(55.dp)
                ) {
                    Text("Apply", color = Color.White)
                }
            }

            Spacer(Modifier.height(20.dp))

            //---------------------------------------------------------
            // AVAILABLE PROMO CODES LIST
            //---------------------------------------------------------
            Text("Available Offers", color = Color(0xFF5C4033), fontSize = 18.sp)
            Spacer(Modifier.height(12.dp))

            promoList.forEach { promo ->

                val isSelected = promo.code == selectedCode

                Column(
                    Modifier
                        .fillMaxWidth()
                        .border(
                            2.dp,
                            if (isSelected) Color(0xFF5C4033) else Color.LightGray,
                            RoundedCornerShape(18.dp)
                        )
                        .background(if (isSelected) Color(0xFFF5E6CF) else Color.White)
                        .clickable { selectedCode = promo.code }
                        .padding(16.dp)
                ) {

                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Column(Modifier.weight(1f)) {
                            Text(promo.code, color = Color(0xFF5C4033), fontWeight = FontWeight.Bold)
                            Text(promo.description, fontSize = 12.sp, color = Color.Gray)

                            if (promo.minOrder > 0) {
                                Text(
                                    "Min Order: ₹${promo.minOrder}",
                                    fontSize = 10.sp,
                                    color = Color.DarkGray
                                )
                            }
                        }

                        Column(horizontalAlignment = Alignment.End) {

                            Text(
                                promo.discount,
                                fontSize = 12.sp,
                                modifier = Modifier
                                    .background(Color(0xFFDBF2D4), RoundedCornerShape(50))
                                    .padding(horizontal = 10.dp, vertical = 4.dp),
                                color = Color(0xFF1E7A1B)
                            )

                            if (isSelected) {
                                Text("✔", fontSize = 22.sp, color = Color(0xFF5C4033))
                            }
                        }
                    }
                }

                Spacer(Modifier.height(10.dp))
            }
        }

        //---------------------------------------------------------
        // APPLY SELECTED PROMO BUTTON
        //---------------------------------------------------------
        Button(
            onClick = { nav.navigate("cart") },
            enabled = selectedCode != null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (selectedCode != null) Color(0xFF5C4033) else Color.Gray
            )
        ) {
            Text("Apply Selected Promo", color = Color.White)
        }
    }
}
