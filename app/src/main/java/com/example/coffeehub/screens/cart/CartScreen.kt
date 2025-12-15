package com.example.coffeehub.screens.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.LocalCafe
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
import com.example.coffeehub.cart.CartManager

@Composable
fun CartScreen(nav: NavController) {

    val brown = Color(0xFF5C4033)
    val cream = Color(0xFFF5E6CF)

    val cartItems = CartManager.cartItems

    val subtotal = cartItems.sumOf { it.price * it.quantity }
    val tax = (subtotal * 0.05).toInt()
    val total = subtotal + tax

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        // TOP BAR
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { nav.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, null)
            }
            Spacer(Modifier.width(12.dp))
            Text("My Cart", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        }

        // CART LIST
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
        ) {

            if (cartItems.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Your cart is empty ☕", color = Color.Gray)
                }
            }

            cartItems.forEach { item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = cream)
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .clip(RoundedCornerShape(14.dp))
                                .background(Color.White),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.LocalCafe,
                                null,
                                tint = brown,
                                modifier = Modifier.size(30.dp)
                            )
                        }

                        Spacer(Modifier.width(12.dp))

                        Column(Modifier.weight(1f)) {
                            Text(item.name, fontWeight = FontWeight.Bold)
                            Text(item.size, fontSize = 12.sp, color = Color.Gray)
                            Text("₹${item.price}", fontWeight = FontWeight.SemiBold, color = brown)
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {

                            Icon(
                                Icons.Default.Delete,
                                null,
                                tint = Color.Red,
                                modifier = Modifier
                                    .size(22.dp)
                                    .clickable { CartManager.removeItem(item.id) }
                            )

                            Spacer(Modifier.height(6.dp))

                            Row(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(50))
                                    .background(Color.White)
                                    .padding(horizontal = 10.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    "-",
                                    fontSize = 18.sp,
                                    modifier = Modifier.clickable {
                                        CartManager.updateQty(item.id, -1)
                                    }
                                )
                                Text(
                                    item.quantity.toString(),
                                    modifier = Modifier.padding(horizontal = 12.dp),
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    "+",
                                    fontSize = 18.sp,
                                    modifier = Modifier.clickable {
                                        CartManager.updateQty(item.id, 1)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }

        // TOTAL
        if (cartItems.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF8F3E9))
                    .padding(18.dp)
            ) {

                Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                    Text("Subtotal")
                    Text("₹$subtotal")
                }

                Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                    Text("Tax (5%)")
                    Text("₹$tax")
                }

                Spacer(Modifier.height(8.dp))

                Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                    Text("Total", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Text("₹$total", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }

                Spacer(Modifier.height(14.dp))

                Button(
                    onClick = { nav.navigate("payment_options") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(brown)
                ) {
                    Text("Proceed to Payment", color = Color.White)
                }
            }
        }
    }
}
