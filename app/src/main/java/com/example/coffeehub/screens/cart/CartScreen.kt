package com.example.coffeehub.screens.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
    val green = Color(0xFF1E7A1B)
    val lightBg = Color(0xFFF9F9F9)

    val cartItems = CartManager.cartItems
    val subtotal = CartManager.subtotal
    val tax = CartManager.tax
    val discount = CartManager.discountAmount.value
    val total = CartManager.totalAmount

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(lightBg)
    ) {

        // 🔝 TOP BAR (POLISHED)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { nav.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, null)
            }
            Spacer(Modifier.width(10.dp))
            Text(
                "My Cart",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = brown
            )
        }

        // 🛒 CART ITEMS
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
                        .padding(vertical = 8.dp),
                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.cardElevation(6.dp),
                    colors = CardDefaults.cardColors(containerColor = cream)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        // ☕ ICON
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .clip(RoundedCornerShape(16.dp))
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

                        Spacer(Modifier.width(14.dp))

                        // ☕ DETAILS
                        Column(Modifier.weight(1f)) {
                            Text(item.name, fontWeight = FontWeight.Bold, color = brown)
                            Text(item.size, fontSize = 12.sp, color = Color.Gray)
                            Text("₹${item.price}", color = brown, fontWeight = FontWeight.SemiBold)
                        }

                        // ➕➖ ACTIONS
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {

                            Icon(
                                Icons.Default.Delete,
                                null,
                                tint = Color.Red,
                                modifier = Modifier
                                    .size(22.dp)
                                    .clickable { CartManager.removeItem(item.id) }
                            )

                            Spacer(Modifier.height(8.dp))

                            Row(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(50))
                                    .background(Color(0xFFF2F2F2))
                                    .padding(horizontal = 12.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    "-", fontSize = 18.sp,
                                    modifier = Modifier.clickable {
                                        CartManager.updateQty(item.id, -1)
                                    }
                                )
                                Text(
                                    item.quantity.toString(),
                                    modifier = Modifier.padding(horizontal = 14.dp),
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    "+", fontSize = 18.sp,
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

        // 🎟 PROMO CARD
        if (cartItems.isNotEmpty()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clickable { nav.navigate("promo") },
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        if (CartManager.appliedPromoCode.value != null)
                            "🎉 Promo Applied: ${CartManager.appliedPromoCode.value}"
                        else
                            "🎟 Apply Promo Code",
                        color = brown,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        // 💰 BILLING SECTION (POLISHED)
        if (cartItems.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(18.dp)
            ) {

                Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                    Text("Subtotal", color = Color.Gray)
                    Text("₹$subtotal")
                }

                Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                    Text("Tax (5%)", color = Color.Gray)
                    Text("₹$tax")
                }

                if (discount > 0) {
                    Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                        Text("Promo Discount", color = green)
                        Text("-₹$discount", color = green)
                    }
                }

                Divider(
                    modifier = Modifier.padding(vertical = 10.dp),
                    color = Color.LightGray
                )

                Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                    Text("Total", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Text(
                        "₹$total",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = brown
                    )
                }

                Spacer(Modifier.height(14.dp))

                Button(
                    onClick = { nav.navigate("payment_options") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors(brown)
                ) {
                    Text("Proceed to Payment", color = Color.White, fontSize = 16.sp)
                }
            }
        }
    }
}
