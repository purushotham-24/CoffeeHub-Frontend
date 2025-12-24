package com.example.coffeehub.screens.coffee
import com.example.coffeehub.cart.CartManager

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.coffeehub.data.getCoffeeById
import com.example.coffeehub.coffee.screens.favorites.FavoritesManager
import kotlin.math.max

@Composable
fun CoffeeDetails(nav: NavController, id: String) {

    val coffee = getCoffeeById(id)
    val context = LocalContext.current

    // If no coffee -> Show error screen
    if (coffee == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Coffee not found", color = Color.Red)
        }
        return
    }

    var qty by remember { mutableStateOf(1) }

    // ❤️ Read favorite state from storage
    var isFav by remember {
        mutableStateOf(FavoritesManager.isFavorite(context, coffee.id))
    }

    val Brown = Color(0xFF5C4033)
    val Cream = Color(0xFFF5E6CF)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        // ⭐ TOP IMAGE + BACK BUTTON + FAV BUTTON
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(330.dp)
                .shadow(4.dp)
        ) {
            AsyncImage(
                model = coffee.image,
                contentDescription = coffee.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                // Back button
                IconButton(
                    onClick = { nav.popBackStack() },
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.80f))
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Brown,
                        modifier = Modifier.size(26.dp)
                    )
                }

                // ❤️ Favorite button (CONNECTED TO SAVED FAVORITES)
                IconButton(
                    onClick = {
                        FavoritesManager.toggleFavorite(context, coffee.id)
                        isFav = !isFav
                    },
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.80f))
                ) {
                    Icon(
                        imageVector = if (isFav)
                            Icons.Default.Favorite
                        else
                            Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (isFav) Color.Red else Brown,
                        modifier = Modifier.size(26.dp)
                    )
                }
            }
        }

        // ⭐ DETAILS SECTION
        Column(modifier = Modifier.padding(20.dp)) {

            Text(
                coffee.name,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Brown
            )

            Spacer(Modifier.height(6.dp))



            Spacer(Modifier.height(20.dp))

            // ⭐ Choose size button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(4.dp, RoundedCornerShape(12.dp))
                    .clip(RoundedCornerShape(12.dp))
                    .background(Cream)
                    .clickable { nav.navigate("cup_size/${coffee.id}") }
                    .padding(vertical = 14.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Choose Size",
                    color = Brown,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(Modifier.height(20.dp))

            // ⭐ Quantity title
            Text(
                "Quantity",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Brown
            )

            Spacer(Modifier.height(10.dp))

            // ⭐ Quantity control buttons
            Row(verticalAlignment = Alignment.CenterVertically) {

                QuantityButton("-") { qty = max(1, qty - 1) }

                Spacer(Modifier.width(20.dp))

                Text(
                    qty.toString(),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.width(20.dp))

                QuantityButton("+") { qty++ }
            }

            Spacer(Modifier.height(30.dp))

            // ⭐ ADD TO CART button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .shadow(6.dp, RoundedCornerShape(50.dp))
                    .clip(RoundedCornerShape(50.dp))
                    .background(Brown)
                    .clickable {
                        CartManager.addItem(
                            id = coffee.id,
                            name = coffee.name,
                            size = "Medium",        // later can be dynamic
                            price = coffee.price,
                            quantity = qty
                        )

                        nav.navigate("cart")
                    }

                ,contentAlignment = Alignment.Center
            ) {
                Text(
                    "Add to Cart • ₹${coffee.price * qty}",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

// ⭐ Reusable Quantity Button
@Composable
fun QuantityButton(symbol: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .shadow(4.dp, CircleShape)
            .clip(CircleShape)
            .background(Color(0xFFF5E6CF))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            symbol,
            fontSize = 28.sp,
            color = Color(0xFF5C4033),
            fontWeight = FontWeight.Bold
        )
    }
}
