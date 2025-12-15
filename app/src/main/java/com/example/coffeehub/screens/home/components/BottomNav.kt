package com.example.coffeehub.screens.home.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun BottomNav(nav: NavController) {

    val items = listOf(
        BottomNavItem("Home", Icons.Filled.Home, "home"),
        BottomNavItem("Search", Icons.Filled.Search, "search"),
        BottomNavItem("Track", Icons.Filled.ShoppingCart, "Track"),
        BottomNavItem("Profile", Icons.Filled.Person, "profile")
    )

    NavigationBar(containerColor = Color.White) {

        items.forEach { item ->
            val selected = nav.currentDestination?.route == item.route
            val tint by animateColorAsState(if (selected) Color(0xFF5C4033) else Color.Gray, label = "")

            NavigationBarItem(
                selected = selected,
                onClick = { nav.navigate(item.route) },
                icon = {
                    Icon(item.icon, contentDescription = item.label, tint = tint)
                },
                label = {
                    Text(item.label, color = tint, fontSize = MaterialTheme.typography.labelSmall.fontSize)
                }
            )
        }
    }
}

data class BottomNavItem(val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector, val route: String)
