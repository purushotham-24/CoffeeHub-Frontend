package com.example.coffeehub.screens.coffee

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.coffeehub.data.coldCoffees
import com.example.coffeehub.model.Coffee

@Composable
fun ColdCoffeeList(navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Cold Coffee", style = MaterialTheme.typography.headlineSmall)
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(coldCoffees) { coffee ->
                ColdCoffeeCard(coffee, navController)
            }
        }
    }
}

@Composable
fun ColdCoffeeCard(coffee: Coffee, navController: NavController) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
    ) {
        Box {

            Column {

                AsyncImage(
                    model = coffee.image,
                    contentDescription = coffee.name,
                    modifier = Modifier
                        .height(110.dp)
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )

                Column(Modifier.padding(10.dp)) {
                    Text(coffee.name, style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(4.dp))
                    Text("₹${coffee.price}")
                    Spacer(Modifier.height(4.dp))
                    Text("⭐ ${coffee.rating}")
                }
            }

            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(10.dp)
                    .size(36.dp)
                    .background(Color(0xFF5C4033), CircleShape)
                    .clickable {
                        navController.navigate("coffee_details/${coffee.id}")
                    },
                contentAlignment = Alignment.Center
            ) {
                Text("+", color = Color.White)
            }
        }
    }
}
