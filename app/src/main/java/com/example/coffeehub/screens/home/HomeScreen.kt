package com.example.coffeehub.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.coffeehub.data.filterCoffees
import com.example.coffeehub.model.Coffee

// ---------------------------------------------------------------------------
// HOME SCREEN
// ---------------------------------------------------------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(nav: NavController) {

    val brown = Color(0xFF5C4033)
    val cream = Color(0xFFF5E6CF)
    val popular = filterCoffees.take(2)

    Scaffold(
        topBar = { HeaderSection(nav, brown, cream) },
        bottomBar = { BottomNavBar(nav) }
    ) { pad ->

        LazyColumn(
            Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(pad)
        ) {

            // ⭐ CROWD PREDICTION CARD
            item { CrowdPredictionCard(nav) }

            // ⭐ CATEGORIES
            item {
                Text(
                    "Categories",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 20.dp, top = 12.dp, bottom = 10.dp)
                )
            }

            val categories = listOf("Filter Coffee", "Cappuccino", "Cold Coffee", "Latte")

            item {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .height(170.dp)
                        .padding(horizontal = 18.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(categories) { name ->
                        CategoryCard(name, brown, cream, nav)
                    }
                }
            }

            // ⭐ NEARBY BANNER
            item { Spacer(Modifier.height(18.dp)) }
            item { NearbyBanner(nav) }

            // ⭐ POPULAR
            item { Spacer(Modifier.height(22.dp)) }
            item {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 18.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Popular Coffee", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text(
                        "See All",
                        color = brown,
                        fontSize = 14.sp,
                        modifier = Modifier.clickable { nav.navigate("popular_coffee") }
                    )
                }
            }

            items(popular.size) { i ->
                HomeCoffeeCard(popular[i], nav)
                Spacer(Modifier.height(10.dp))
            }

            // ⭐ BOOKING BUTTONS
            item { Spacer(Modifier.height(30.dp)) }
            item {
                Column(Modifier.padding(horizontal = 18.dp)) {

                    Button(
                        onClick = { nav.navigate("seat_map") }, // DIRECT TO SEAT MAP
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp),
                        colors = ButtonDefaults.buttonColors(brown)
                    ) {
                        Text("Reserve a Seat", color = Color.White)
                    }

                    Spacer(Modifier.height(12.dp))

                    Button(
                        onClick = { nav.navigate("workspace_options") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp),
                        colors = ButtonDefaults.buttonColors(brown)
                    ) {
                        Text("Book Workspace", color = Color.White)
                    }
                }
            }

            item { Spacer(Modifier.height(70.dp)) }
        }
    }
}

// ---------------------------------------------------------------------------
// HEADER SECTION
// ---------------------------------------------------------------------------
@Composable
fun HeaderSection(nav: NavController, brown: Color, cream: Color) {

    Box(
        Modifier
            .fillMaxWidth()
            .background(Brush.verticalGradient(listOf(brown, brown.copy(.9f))))
            .padding(20.dp)
    ) {

        Column {

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.LocationOn, "", tint = cream)
                    Spacer(Modifier.width(8.dp))
                    Column {
                        Text("Location", color = cream.copy(.7f), fontSize = 12.sp)
                        Text("Chennai", color = Color.White, fontSize = 20.sp)
                    }
                }

                Row {
                    IconButton({ nav.navigate("notifications") }) {
                        Icon(Icons.Default.Notifications, "", tint = Color.White)
                    }
                    IconButton({ nav.navigate("cart") }) {
                        Icon(Icons.Default.ShoppingCart, "", tint = Color.White)
                    }
                }
            }

            Spacer(Modifier.height(18.dp))

            Box(
                Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .background(Color.White)
                    .clickable { nav.navigate("search") }
                    .padding(start = 15.dp),
                Alignment.CenterStart
            ) {
                Text("Search for coffee...", color = Color.Gray)
            }
        }
    }
}

// ---------------------------------------------------------------------------
// CROWD PREDICTION CARD
// ---------------------------------------------------------------------------
data class CrowdData(val level: Int, val label: String, val color: Color)

@Composable
fun CrowdPredictionCard(nav: NavController) {

    val hour = java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY)

    val current = when {
        hour in 12..13 -> CrowdData(92, "Peak", Color(0xFFE53935))
        hour in 9..10 -> CrowdData(78, "High", Color(0xFFFF9800))
        hour in 17..18 -> CrowdData(82, "High", Color(0xFFFF9800))
        hour in 8..16 -> CrowdData(55, "Medium", Color(0xFFFFD700))
        else -> CrowdData(28, "Low", Color(0xFF4CAF50))
    }

    val brown = Color(0xFF5C4033)

    Box(
        Modifier
            .padding(horizontal = 18.dp, vertical = 14.dp)
            .clip(RoundedCornerShape(22.dp))
            .background(Brush.linearGradient(listOf(brown, brown.copy(.85f))))
            .clickable { nav.navigate("crowd_prediction") }
            .padding(18.dp)
    ) {

        Column {

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Groups, "", tint = Color.White)
                    Spacer(Modifier.width(8.dp))
                    Text("Crowd Status", color = Color.White)
                }

                Row(
                    Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color.White.copy(0.2f))
                        .padding(horizontal = 10.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(Color.Green)
                    )
                    Spacer(Modifier.width(6.dp))
                    Text("view", color = Color.White, fontSize = 12.sp)
                }
            }

            Spacer(Modifier.height(12.dp))

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Column {
                    Text("${current.level}%", color = Color.White, fontSize = 28.sp)
                    Text("${current.label} crowd level", color = Color(0xFFF5E6CF))
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .background(current.color),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Groups, "", tint = Color.White)
                    }
                    Spacer(Modifier.height(6.dp))
                    Text("View forecast →", color = Color(0xFFF5E6CF), fontSize = 12.sp)
                }
            }
        }
    }
}

// ---------------------------------------------------------------------------
// CATEGORY CARD
// ---------------------------------------------------------------------------
@Composable
fun CategoryCard(name: String, brown: Color, cream: Color, nav: NavController) {

    Box(
        Modifier
            .height(60.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(cream)
            .clickable {
                when (name) {
                    "Filter Coffee" -> nav.navigate("filter_coffee")
                    "Cappuccino" -> nav.navigate("cappuccino_list")
                    "Cold Coffee" -> nav.navigate("cold_coffee_list")
                    "Latte" -> nav.navigate("latte_list")
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Text(name, color = brown, fontWeight = FontWeight.Bold)
    }
}

// ---------------------------------------------------------------------------
// NEARBY BANNER
// ---------------------------------------------------------------------------
@Composable
fun NearbyBanner(nav: NavController) {
    Box(
        Modifier
            .fillMaxWidth()
            .height(85.dp)
            .padding(horizontal = 18.dp)
            .clip(RoundedCornerShape(22.dp))
            .background(
                Brush.horizontalGradient(
                    listOf(Color(0xFF5C4033), Color(0xFF7A5543))
                )
            )
            .clickable { nav.navigate("nearby") }
            .padding(20.dp),
        Alignment.CenterStart
    ) {
        Column {
            Text("Find us nearby", color = Color(0xFFF5E6CF), fontSize = 13.sp)
            Text("Explore Locations", color = Color.White, fontSize = 17.sp, fontWeight = FontWeight.Bold)
        }
    }
}

// ---------------------------------------------------------------------------
// POPULAR COFFEE CARD
// ---------------------------------------------------------------------------
@Composable
fun HomeCoffeeCard(c: Coffee, nav: NavController) {

    Card(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp)
            .height(200.dp)
            .clickable { nav.navigate("filter_coffee") },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(Color(0xFFF5E6CF))
    ) {

        Box {
            Column {
                AsyncImage(
                    model = c.image,
                    contentDescription = c.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth().height(110.dp)
                )

                Column(Modifier.padding(12.dp)) {
                    Text(c.name, color = Color(0xFF5C4033), fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Text(c.description, color = Color.DarkGray, fontSize = 13.sp)
                    Text("₹${c.price}", color = Color(0xFF5C4033), fontSize = 16.sp)
                }
            }

            Box(
                Modifier
                    .align(Alignment.BottomEnd)
                    .padding(12.dp)
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF5C4033))
                    .clickable { nav.navigate("filter_coffee") },
                contentAlignment = Alignment.Center
            ) {
                Text("+", color = Color.White, fontSize = 22.sp)
            }
        }
    }
}

// ---------------------------------------------------------------------------
// BOTTOM NAV
// ---------------------------------------------------------------------------
@Composable
fun BottomNavBar(nav: NavController) {
    NavigationBar(containerColor = Color(0xFFF5E6CF)) {
        NavigationBarItem(
            selected = true,
            onClick = { nav.navigate("home") },
            icon = { Icon(Icons.Filled.Home, "") },
            label = { Text("Home") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { nav.navigate("search") },
            icon = { Icon(Icons.Filled.Search, "") },
            label = { Text("Search") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { nav.navigate("tracking") },
            icon = { Icon(Icons.Filled.ShoppingCart, "") },
            label = { Text("Track") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { nav.navigate("favorites") },
            icon = { Icon(Icons.Filled.Favorite, "") },
            label = { Text("Fav") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { nav.navigate("profile") },
            icon = { Icon(Icons.Filled.Person, "") },
            label = { Text("Profile") }
        )
    }
}
