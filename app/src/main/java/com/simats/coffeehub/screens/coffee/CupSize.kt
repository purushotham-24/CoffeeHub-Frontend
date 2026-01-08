package com.simats.coffeehub.screens.coffee

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.simats.coffeehub.cart.CartManager
import com.simats.coffeehub.data.getCoffeeById

// ---------------- SIZE MODEL ----------------
data class CupSizeOption(
    val id: String,
    val name: String,
    val volume: String,
    val extraPrice: Int
)

@Composable
fun CupSize(navController: NavController, id: String) {

    // ✅ GET COFFEE SAFELY
    val coffee = getCoffeeById(id) ?: return

    var selectedSize by remember { mutableStateOf("medium") }

    val brown = Color(0xFF5C4033)
    val cream = Color(0xFFF8EEDC)
    val gray200 = Color(0xFFE1E1E1)
    val gray500 = Color(0xFF7D7D7D)

    val sizes = listOf(
        CupSizeOption("small", "Small", "250 ml", 0),
        CupSizeOption("medium", "Medium", "350 ml", 20),
        CupSizeOption("large", "Large", "450 ml", 40)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        // ---------------- HEADER ----------------
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(brown)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.ArrowBack,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(28.dp)
                    .clickable { navController.popBackStack() }
            )
            Spacer(Modifier.width(16.dp))
            Text(
                "Select Size",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // ---------------- SIZE OPTIONS ----------------
        Column(Modifier.padding(20.dp)) {

            sizes.forEach { size ->

                val isSelected = selectedSize == size.id

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 14.dp)
                        .shadow(
                            elevation = if (isSelected) 6.dp else 2.dp,
                            shape = RoundedCornerShape(22.dp)
                        )
                        .background(
                            if (isSelected) cream else Color.White,
                            RoundedCornerShape(22.dp)
                        )
                        .border(
                            2.dp,
                            if (isSelected) brown else gray200,
                            RoundedCornerShape(22.dp)
                        )
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) { selectedSize = size.id }
                        .padding(18.dp)
                ) {

                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Column {
                            Text(size.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                            Text(size.volume, color = gray500, fontSize = 14.sp)
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {

                            if (size.extraPrice > 0) {
                                Text(
                                    "+₹${size.extraPrice}",
                                    color = brown,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.padding(end = 12.dp)
                                )
                            }

                            if (isSelected) {
                                Box(
                                    modifier = Modifier
                                        .size(26.dp)
                                        .background(brown, CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(Icons.Default.Check, null, tint = Color.White)
                                }
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(50.dp))

            // ---------------- CONTINUE BUTTON ----------------
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .shadow(6.dp, RoundedCornerShape(50.dp))
                    .background(brown, RoundedCornerShape(50.dp))
                    .clickable {

                        val selected = sizes.first { it.id == selectedSize }

                        CartManager.addItem(
                            id = coffee.id,
                            name = coffee.name,
                            size = selected.name, // Small / Medium / Large
                            price = coffee.price + selected.extraPrice,
                            quantity = 1
                        )

                        navController.navigate("cart")
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Add to Cart • ₹${coffee.price + sizes.first { it.id == selectedSize }.extraPrice}",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
