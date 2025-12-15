package com.example.coffeehub.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import androidx.navigation.NavController

data class CoffeeCardData(
    val id: String,
    val name: String,
    val description: String,
    val price: Int,
    val rating: Double,
    val image: String
)

@Composable
fun CoffeeCard(
    data: CoffeeCardData,
    nav: NavController
) {
    var fav by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { nav.navigate("coffee/details/${data.id}") },
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column {

            // IMAGE
            Box {
                AsyncImage(
                    model = data.image,
                    contentDescription = data.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp),
                    contentScale = ContentScale.Crop
                )

                // ❤️ Favourite Button
                IconButton(
                    onClick = { fav = !fav },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .size(30.dp)
                        .background(Color.White.copy(alpha = 0.9f), CircleShape)
                ) {
                    Icon(
                        imageVector = if (fav) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = "favorite",
                        tint = if (fav) Color.Red else Color.DarkGray
                    )
                }
            }

            // DETAILS
            Column(Modifier.padding(12.dp)) {

                Text(data.name, fontSize = 17.sp, fontWeight = FontWeight.Bold)
                Text(data.description, fontSize = 13.sp, color = Color.Gray)

                Spacer(Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    // ⭐ Rating
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.Star, null, tint = Color(0xFFFFC100), modifier = Modifier.size(16.dp))
                        Text("${data.rating}", fontSize = 13.sp, color = Color.Black)
                    }

                    // ₹ PRICE  + ADD BUTTON
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("₹${data.price}", fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF5C4033))

                        IconButton(
                            onClick = { nav.navigate("coffee/sizes/${data.id}") },
                            modifier = Modifier
                                .size(30.dp)
                                .background(Color(0xFF5C4033), CircleShape)
                        ) {
                            Icon(Icons.Filled.Add, null, tint = Color.White)
                        }
                    }
                }
            }
        }
    }
}
