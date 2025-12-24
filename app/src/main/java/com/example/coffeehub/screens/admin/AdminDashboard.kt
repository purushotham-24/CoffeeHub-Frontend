package com.example.coffeehub.screens.admin

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.coffeehub.model.Coffee
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboard(nav: NavController) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    /* ---------- THEME COLORS ---------- */
    val brown = Color(0xFF5C4033)
    val cream = Color(0xFFF5E6CF)

    /* ---------- LOAD COFFEES FROM SERVER ---------- */
    LaunchedEffect(Unit) {
        scope.launch {
            AdminManager.loadFromServer()
        }
    }

    val coffees = AdminManager.coffees
    var showDialog by remember { mutableStateOf(false) }
    var editCoffee by remember { mutableStateOf<Coffee?>(null) }

    Scaffold(

        /* ---------- TOP BAR ---------- */
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Admin Dashboard",
                        color = Color.White
                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            // ✅ CLEAR SESSION & LOGOUT
                            val prefs = context.getSharedPreferences(
                                "coffeehub_prefs",
                                Context.MODE_PRIVATE
                            )
                            prefs.edit().clear().apply()

                            nav.navigate("login") {
                                popUpTo(0) { inclusive = true }
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Logout,
                            contentDescription = "Exit",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = brown
                )
            )
        },

        /* ---------- ADD BUTTON ---------- */
        floatingActionButton = {
            FloatingActionButton(
                containerColor = brown,
                onClick = {
                    editCoffee = null
                    showDialog = true
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add Coffee",
                    tint = Color.White
                )
            }
        }

    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(cream)
                .padding(padding)
        ) {

            if (coffees.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No coffees available ☕")
                }
            } else {
                LazyColumn(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    items(coffees) { coffee ->
                        CoffeeAdminCard(
                            coffee = coffee,
                            brown = brown,
                            onEdit = {
                                editCoffee = coffee
                                showDialog = true
                            },
                            onDelete = {
                                scope.launch {
                                    AdminManager.deleteCoffee(coffee)
                                }
                            }
                        )
                    }
                }
            }
        }
    }

    /* ---------- ADD / EDIT DIALOG ---------- */
    if (showDialog) {
        AddEditCoffeeDialog(
            coffee = editCoffee,
            onDismiss = { showDialog = false },
            onSave = { coffee ->
                scope.launch {
                    if (editCoffee == null)
                        AdminManager.addCoffee(coffee)
                    else
                        AdminManager.updateCoffee(coffee)
                }
                showDialog = false
            }
        )
    }
}

/* ================================================= */
/* ================= COFFEE CARD =================== */
/* ================================================= */

@Composable
fun CoffeeAdminCard(
    coffee: Coffee,
    brown: Color,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {

            Text(
                text = coffee.name,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = coffee.description,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("₹${coffee.price}", color = brown)
                Text(coffee.category)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = onEdit) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "Edit",
                        tint = brown
                    )
                }
                IconButton(onClick = onDelete) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Delete",
                        tint = Color.Red
                    )
                }
            }
        }
    }
}
