package com.simats.coffeehub.screens.search

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.simats.coffeehub.data.findFirstCoffeeMatching

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun SearchScreen(nav: NavController) {

    val brown = Color(0xFF5C4033)
    val cream = Color(0xFFF5E6CF)

    var searchQuery by remember { mutableStateOf("") }

    // UPDATED LISTS
    val recent = listOf("Cappuccino", "Latte", "Filter Coffee")
    val trending = listOf("Filter Coffee", "Latte", "Cappuccino")

    val focusManager = LocalFocusManager.current

    fun navigateForQuery(query: String) {
        val found = findFirstCoffeeMatching(query)
        if (found != null) {
            nav.navigate("coffee_details/${found.id}")
        } else {
            val encoded = query.trim().replace(" ", "+")
            nav.navigate("search-results?q=$encoded")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())   // ⭐ SCROLL ENABLED
            .background(Color.White)
    ) {

        //-------------------- TOP BAR --------------------//
        Box(
            modifier = Modifier
                .background(
                    Brush.verticalGradient(
                        listOf(brown, Color(0xFF704A37))
                    )
                )
                .padding(top = 45.dp, bottom = 25.dp, start = 22.dp, end = 22.dp)
        ) {

            Column {

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "back",
                        tint = Color.White,
                        modifier = Modifier
                            .size(26.dp)
                            .clickable { nav.popBackStack() }
                    )
                    Spacer(Modifier.width(14.dp))
                    Text(
                        "Search",
                        fontSize = 24.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(Modifier.height(16.dp))

                // Search Bar
                TextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search coffee...", color = Color.Gray) },
                    leadingIcon = { Icon(Icons.Default.Search, null, tint = brown) },
                    singleLine = true,
                    shape = RoundedCornerShape(30.dp),
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        disabledContainerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            focusManager.clearFocus()
                            if (searchQuery.isNotBlank()) navigateForQuery(searchQuery)
                        }
                    )
                )
            }
        }

        Spacer(Modifier.height(22.dp))

        //-------------------- RECENT --------------------//
        Text(
            "Recent Searches",
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold,
            color = brown,
            modifier = Modifier.padding(start = 22.dp, bottom = 10.dp)
        )

        FlowRow(
            modifier = Modifier.padding(horizontal = 18.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            recent.forEach { item ->
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50.dp))
                        .background(cream)
                        .clickable {
                            searchQuery = item
                            navigateForQuery(item)
                        }
                        .padding(horizontal = 18.dp, vertical = 8.dp)
                ) {
                    Text(item, color = brown, fontWeight = FontWeight.Medium, fontSize = 14.sp)
                }
            }
        }

        Spacer(Modifier.height(28.dp))

        //-------------------- TRENDING --------------------//
        Text(
            "Trending Now",
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold,
            color = brown,
            modifier = Modifier.padding(start = 22.dp, bottom = 14.dp)
        )

        trending.forEachIndexed { index, item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(18.dp))
                    .background(cream.copy(alpha = 0.55f))
                    .clickable {
                        searchQuery = item
                        navigateForQuery(item)
                    }
                    .animateContentSize()
                    .padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(brown),
                    contentAlignment = Alignment.Center
                ) {
                    Text("${index + 1}", color = Color.White, fontWeight = FontWeight.Bold)
                }
                Spacer(Modifier.width(14.dp))
                Text(item, fontSize = 17.sp, color = brown, fontWeight = FontWeight.SemiBold)
            }

            Spacer(Modifier.height(10.dp))
        }

        Spacer(Modifier.height(30.dp))

        //-------------------- RECOMMENDED (FILTER COFFEE) --------------------//
        Text(
            "Recommended For You",
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold,
            color = brown,
            modifier = Modifier.padding(start = 22.dp, bottom = 12.dp)
        )

        repeat(1) {
            RecommendedCoffeeCard()
        }

        Spacer(Modifier.height(40.dp))
    }
}

//==================== RECOMMENDED COFFEE CARD ====================//
@Composable
fun RecommendedCoffeeCard() {
    val brown = Color(0xFF5C4033)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp, vertical = 6.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFFF5E6CF))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(brown.copy(.9f)),
            contentAlignment = Alignment.Center
        ) {
            Text("☕", fontSize = 22.sp)
        }

        Spacer(Modifier.width(14.dp))

        Column {
            Text("Filter Coffee", fontWeight = FontWeight.Bold, color = brown)
            Text("Strong • ₹120", color = Color.DarkGray, fontSize = 13.sp)
        }
    }
}
