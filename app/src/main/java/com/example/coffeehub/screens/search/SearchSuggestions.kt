package com.example.coffeehub.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun SearchSuggestionsScreen(nav: NavController) {

    var searchQuery by remember { mutableStateOf("cap") }

    val suggestions = listOf(
        Suggestion("Cappuccino", "Hot Coffee", 12),
        Suggestion("Caramel Cappuccino", "Specialty", 5),
        Suggestion("Caffe Latte", "Hot Coffee", 8),
        Suggestion("Caramel Macchiato", "Specialty", 6),
        Suggestion("Cafe Mocha", "Hot Coffee", 7)
    )

    Column(Modifier.fillMaxSize().background(Color.White)) {

        // 🔥 Top Bar
        Column(
            Modifier
                .fillMaxWidth()
                .background(Color(0xFF5C4033))
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("←", color = Color.White, fontSize = 28.sp, modifier = Modifier.clickable {
                    nav.popBackStack()
                })
                Spacer(Modifier.width(12.dp))
                Text("Search", color = Color.White, fontSize = 20.sp)
            }

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search for coffee...", color = Color.Gray) },
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(30.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = Color(0xFF5C4033),
                    unfocusedBorderColor = Color.White,
                    cursorColor = Color(0xFF5C4033)
                )
            )
        }

        Spacer(Modifier.height(14.dp))

        Text(
            "Showing suggestions for \"$searchQuery\"",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(Modifier.height(12.dp))

        LazyColumn(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(suggestions) { item ->
                SuggestionTile(item, searchQuery) {
                    nav.navigate("search-results?q=${item.text}")
                }
            }
        }

        Spacer(Modifier.height(10.dp))

        Text(
            "Popular Categories",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF5C4033),
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(Modifier.height(12.dp))

        Column(Modifier.padding(horizontal = 16.dp)) {
            CategoryBox("Filter Coffee", "Traditional South Indian")
            Spacer(Modifier.height(10.dp))
            CategoryBox("Cappuccino", "Italian Classic")
            Spacer(Modifier.height(10.dp))
            CategoryBox("Cold Coffee", "Iced Beverages")
            Spacer(Modifier.height(10.dp))
            CategoryBox("Latte", "Milk Based")
        }
    }
}

//================ COMPONENTS ================//

@Composable
fun SuggestionTile(item: Suggestion, highlight: String, onClick: () -> Unit) {
    Row(
        Modifier.fillMaxWidth().clickable { onClick() }.padding(12.dp)
    ) {
        Text("🔍", fontSize = 20.sp)
        Spacer(Modifier.width(10.dp))

        Column(Modifier.weight(1f)) {
            item.text.split(highlight, ignoreCase = true).forEachIndexed { index, part ->
                Text(
                    text = if (index < item.text.split(highlight).size - 1) "$part$highlight" else part,
                    fontWeight = if (index < item.text.split(highlight).size - 1) FontWeight.Bold else FontWeight.Normal,
                    color = Color(0xFF5C4033)
                )
            }
            Text("${item.category} • ${item.count} items", fontSize = 12.sp, color = Color.Gray)
        }

        Text("↗", fontSize = 22.sp, color = Color.Gray)
    }
}

@Composable
fun CategoryBox(title: String, desc: String) {
    Box(
        Modifier.fillMaxWidth().background(Color(0xFFF5E6CF), RoundedCornerShape(18.dp)).padding(14.dp)
    ) {
        Column {
            Text(title, color = Color(0xFF5C4033), fontWeight = FontWeight.Bold)
            Text(desc, fontSize = 12.sp, color = Color.Gray)
        }
    }
}

data class Suggestion(
    val text: String,
    val category: String,
    val count: Int
)
