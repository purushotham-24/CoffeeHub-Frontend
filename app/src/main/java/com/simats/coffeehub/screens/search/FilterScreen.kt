package com.simats.coffeehub.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight      // ✅ import fixed
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterScreen(nav: NavController) {

    val brown = Color(0xFF5C4033)
    val cream = Color(0xFFF5E6CF)

    var selectedCategory by remember { mutableStateOf(listOf<String>()) }
    var selectedPrice by remember { mutableStateOf("") }
    var selectedRating by remember { mutableStateOf(0) }
    var selectedDietary by remember { mutableStateOf(listOf<String>()) }

    val categories = listOf("Hot Coffee", "Cold Coffee", "Tea", "Smoothies", "Snacks", "Desserts")
    val priceRanges = listOf("Under ₹100", "₹100 - ₹200", "₹200 - ₹300", "Above ₹300")
    val dietaryOptions = listOf("Vegan", "Sugar Free", "Dairy Free", "Organic")

    fun toggleCategory(item: String) {
        selectedCategory =
            if (item in selectedCategory) selectedCategory - item else selectedCategory + item
    }

    fun toggleDietary(item: String) {
        selectedDietary =
            if (item in selectedDietary) selectedDietary - item else selectedDietary + item
    }

    fun reset() {
        selectedCategory = emptyList()
        selectedPrice = ""
        selectedRating = 0
        selectedDietary = emptyList()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Filters", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = brown),
                navigationIcon = {
                    Text(
                        "< Back",
                        color = cream,
                        modifier = Modifier
                            .padding(start = 14.dp)
                            .clickable { nav.popBackStack() }
                    )
                },
                actions = {
                    Text(
                        "Clear All",
                        color = cream,
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .clickable { reset() }
                    )
                }
            )
        },
        bottomBar = {
            Button(
                onClick = { nav.popBackStack() }, // you can change to nav.navigate("search/results") later
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(55.dp),
                colors = ButtonDefaults.buttonColors(brown)
            ) {
                Text("Apply Filters", color = Color.White, fontSize = 16.sp)
            }
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(18.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {

            // CATEGORY
            SectionTitle("Category")
            FilterGrid(
                list = categories,
                selected = selectedCategory,
                onClick = { toggleCategory(it) }
            )

            // PRICE
            SectionTitle("Price Range")
            FilterList(
                list = priceRanges,
                selected = selectedPrice,
                onClick = { selectedPrice = it }
            )

            // RATING
            SectionTitle("Minimum Rating")
            RatingSelector(
                current = selectedRating,
                onSelect = { selectedRating = it }
            )

            // DIETARY
            SectionTitle("Dietary Preference")
            FilterGrid(
                list = dietaryOptions,
                selected = selectedDietary,
                onClick = { toggleDietary(it) }
            )
        }
    }
}

/* ------------ 🔥 Helper Composables --------------- */

@Composable
fun SectionTitle(text: String) {
    Text(
        text = text,
        fontSize = 18.sp,
        color = Color(0xFF5C4033),
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun FilterGrid(
    list: List<String>,
    selected: List<String>,
    onClick: (String) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        list.chunked(2).forEach { rowItems ->
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                rowItems.forEach { item ->
                    // Each chip takes half width inside the Row
                    FilterChip(
                        text = item,
                        selected = item in selected,
                        modifier = Modifier.weight(1f),   // ✅ weight is now passed from Row scope
                        onClick = { onClick(item) }
                    )
                }
            }
        }
    }
}

@Composable
fun FilterList(
    list: List<String>,
    selected: String,
    onClick: (String) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        list.forEach { item ->
            FilterChip(
                text = item,
                selected = selected == item,
                modifier = Modifier.fillMaxWidth(),
                onClick = { onClick(item) }
            )
        }
    }
}

@Composable
fun FilterChip(
    text: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val brown = Color(0xFF5C4033)
    val cream = Color(0xFFF5E6CF)

    Box(
        modifier
            .height(48.dp)
            .border(
                width = 2.dp,
                color = if (selected) brown else Color.Gray,
                shape = RoundedCornerShape(12.dp)
            )
            .background(
                color = if (selected) cream else Color.White,
                shape = RoundedCornerShape(12.dp)
            )
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 10.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(text, color = brown)
    }
}

@Composable
fun RatingSelector(
    current: Int,
    onSelect: (Int) -> Unit
) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        (1..5).forEach { star ->
            Button(
                onClick = { onSelect(star) },
                modifier = Modifier
                    .weight(1f)
                    .height(45.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (current == star) Color(0xFFF5E6CF) else Color.White
                )
            ) {
                Text("${star}★", color = Color(0xFF5C4033))
            }
        }
    }
}
