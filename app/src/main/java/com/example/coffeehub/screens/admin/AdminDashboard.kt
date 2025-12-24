package com.example.coffeehub.screens.admin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.coffeehub.model.Coffee
import kotlinx.coroutines.launch

@Composable
fun AdminDashboard() {

    val scope = rememberCoroutineScope()

    // 🔥 LOAD FROM MYSQL (NOT LOCAL)
    LaunchedEffect(Unit) {
        scope.launch {
            AdminManager.loadFromServer()
        }
    }

    val coffees = AdminManager.coffees
    var showDialog by remember { mutableStateOf(false) }
    var editCoffee by remember { mutableStateOf<Coffee?>(null) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                editCoffee = null
                showDialog = true
            }) {
                Icon(Icons.Default.Add, null)
            }
        }
    ) { pad ->
        LazyColumn(
            modifier = Modifier
                .padding(pad)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(coffees) { coffee ->
                Card {
                    Column(Modifier.padding(12.dp)) {
                        Text(coffee.name, style = MaterialTheme.typography.titleMedium)
                        Text(coffee.description)
                        Text("₹${coffee.price}")
                        Text("Category: ${coffee.category}")

                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            IconButton(onClick = {
                                editCoffee = coffee
                                showDialog = true
                            }) {
                                Icon(Icons.Default.Edit, null)
                            }

                            IconButton(onClick = {
                                scope.launch {
                                    AdminManager.deleteCoffee(coffee)
                                }
                            }) {
                                Icon(Icons.Default.Delete, null)
                            }
                        }
                    }
                }
            }
        }
    }

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
