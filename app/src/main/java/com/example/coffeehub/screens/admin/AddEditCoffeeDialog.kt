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

    var name by remember(coffee) { mutableStateOf(coffee?.name ?: "") }
    var description by remember(coffee) { mutableStateOf(coffee?.description ?: "") }
    var price by remember(coffee) { mutableStateOf(coffee?.price?.toString() ?: "") }
    var image by remember(coffee) { mutableStateOf(coffee?.image ?: "") }
    var category by remember(coffee) { mutableStateOf(coffee?.category ?: "FILTER") }

    var error by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (isEdit) "Edit Coffee" else "Add Coffee") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {

                if (error.isNotEmpty()) {
                    Text(
                        text = error,
                        color = MaterialTheme.colorScheme.error
                    )
                }

                OutlinedTextField(name, { name = it }, label = { Text("Name") })
                OutlinedTextField(description, { description = it }, label = { Text("Description") })
                OutlinedTextField(price, { price = it }, label = { Text("Price") })
                OutlinedTextField(image, { image = it }, label = { Text("Image URL") })
                OutlinedTextField(category, { category = it }, label = { Text("Category") })
            }
        },
        confirmButton = {
            TextButton(onClick = {

                // ✅ VALIDATION (THIS FIXES BAD REQUEST)
                if (name.isBlank() ||
                    description.isBlank() ||
                    price.isBlank() ||
                    category.isBlank()
                ) {
                    error = "All fields except image are required"
                    return@TextButton
                }

                val priceValue = price.toIntOrNull()
                if (priceValue == null || priceValue <= 0) {
                    error = "Enter a valid price"
                    return@TextButton
                }

                // ✅ SAFE TO SAVE
                onSave(
                    Coffee(
                        id = coffee?.id ?: "coffee_${System.currentTimeMillis()}",
                        name = name.trim(),
                        description = description.trim(),
                        price = priceValue,
                        image = image.trim(),
                        category = category.trim()
                    )
                )
            }) {
                Text("SAVE")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("CANCEL") }
        }
    )
}
