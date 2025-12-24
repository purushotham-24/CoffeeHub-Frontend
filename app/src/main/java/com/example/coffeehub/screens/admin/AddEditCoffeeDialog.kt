package com.example.coffeehub.screens.admin

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.coffeehub.model.Coffee

@Composable
fun AddEditCoffeeDialog(
    coffee: Coffee?,
    onDismiss: () -> Unit,
    onSave: (Coffee) -> Unit
) {
    val isEdit = coffee != null

    // 🔥 KEY remember WITH coffee
    var name by remember(coffee) { mutableStateOf(coffee?.name ?: "") }
    var description by remember(coffee) { mutableStateOf(coffee?.description ?: "") }
    var price by remember(coffee) { mutableStateOf(coffee?.price?.toString() ?: "") }
    var image by remember(coffee) { mutableStateOf(coffee?.image ?: "") }
    var category by remember(coffee) { mutableStateOf(coffee?.category ?: "FILTER") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (isEdit) "Edit Coffee" else "Add Coffee") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(name, { name = it }, label = { Text("Name") })
                OutlinedTextField(description, { description = it }, label = { Text("Description") })
                OutlinedTextField(price, { price = it }, label = { Text("Price") })
                OutlinedTextField(image, { image = it }, label = { Text("Image URL") })
                OutlinedTextField(category, { category = it }, label = { Text("Category") })
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onSave(
                    Coffee(
                        id = coffee?.id ?: "coffee_${System.currentTimeMillis()}",
                        name = name,
                        description = description,
                        price = price.toIntOrNull() ?: 0,
                        image = image,
                        category = category
                    )
                )
            }) { Text("SAVE") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("CANCEL") }
        }
    )
}
