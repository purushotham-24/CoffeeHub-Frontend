package com.example.coffeehub.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.coffeehub.data.searchCoffees
import com.example.coffeehub.data.findFirstCoffeeMatching
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.runtime.getValue

@Composable
fun SearchResultsScreen(nav: NavController) {

    val backStackEntry by nav.currentBackStackEntryAsState()
    val rawQuery = backStackEntry?.arguments?.getString("q") ?: ""
    val query = rawQuery.replace('+', ' ')

    val results = if (query.isNotBlank()) searchCoffees(query) else emptyList()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        // ================= TOP BAR ================
        Box(
            Modifier
                .fillMaxWidth()
                .background(Color(0xFF5C4033))
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "←",
                    color = Color.White,
                    fontSize = 26.sp,
                    modifier = Modifier.clickable { nav.popBackStack() }
                )
                Spacer(Modifier.width(12.dp))
                Text("Search Results", fontSize = 20.sp, color = Color.White)
            }
        }

        // ================= FILTER HEADER ================
        Row(
            Modifier
                .fillMaxWidth()
                .background(Color(0xFFF5E6CF))
                .padding(14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Found ${results.size} results for \"$query\"", color = Color(0xFF5C4033))
            Text("Filters", color = Color(0xFF5C4033), modifier = Modifier.clickable {
                nav.navigate("search-filter")
            })
        }

        Spacer(Modifier.height(14.dp))

        // ================= LIST RESULTS ================
        LazyColumn(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(results) { item ->
                ResultCard(item.name) {
                    // navigate to first matching coffee by name (should be this item)
                    nav.navigate("coffee_details/${item.id}")
                }
            }
        }
    }
}

@Composable
fun ResultCard(title: String, onClick: () -> Unit = {}) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5E6CF)),
        modifier = Modifier.fillMaxWidth().clickable { onClick() }
    ) {
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF5C4033),
            modifier = Modifier.padding(18.dp)
        )
    }
}
