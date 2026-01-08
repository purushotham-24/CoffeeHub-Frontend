package com.simats.coffeehub.coffee.screens.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.simats.coffeehub.data.allCoffees
import com.simats.coffeehub.model.Coffee

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedFavoritesScreen(nav: NavController) {

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var favorites by remember { mutableStateOf<List<Coffee>>(emptyList()) }

    // ✅ reload when screen resumes
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                val ids = FavoritesManager.getFavorites(context)
                favorites = allCoffees.filter { ids.contains(it.id) }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Saved Favorites") },
                navigationIcon = {
                    IconButton(onClick = { nav.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
                    }
                }
            )
        }
    ) { pad ->

        if (favorites.isEmpty()) {
            Box(
                Modifier
                    .padding(pad)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No favorites added yet", color = Color.Gray)
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .padding(pad)
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(favorites) { coffee ->
                    FavoriteCard(coffee, nav)
                }
            }
        }
    }
}

@Composable
fun FavoriteCard(coffee: Coffee, nav: NavController) {

    Card(
        shape = RoundedCornerShape(18.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .clickable { nav.navigate("coffee_details/${coffee.id}") }
    ) {
        Box {

            Column {
                AsyncImage(
                    model = coffee.image,
                    contentDescription = coffee.name,
                    modifier = Modifier
                        .height(120.dp)
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )

                Column(Modifier.padding(12.dp)) {
                    Text(
                        coffee.name,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF5C4033)
                    )
                    Spacer(Modifier.height(4.dp))
                    Text("₹${coffee.price}")
                    Spacer(Modifier.height(6.dp))
                    Text("Order Again →", fontSize = 12.sp, color = Color(0xFF5C4033))
                }
            }

            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .size(32.dp)
                    .background(Color.White, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Favorite, null, tint = Color.Red)
            }
        }
    }
}
