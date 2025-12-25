package com.example.coffeehub.screens.admin

import androidx.compose.runtime.mutableStateListOf
import com.example.coffeehub.data.network.RetrofitClient
import com.example.coffeehub.model.Coffee

object AdminManager {

    val coffees = mutableStateListOf<Coffee>()

    // 🔥 LOAD COFFEES FROM BACKEND (SAFE)
    suspend fun loadFromServer() {
        try {
            coffees.clear()
            coffees.addAll(
                RetrofitClient.api.getAllCoffees()
            )
        } catch (e: Exception) {
            // Backend down / no internet
            coffees.clear()
        }
    }

    // 🔥 ADD COFFEE (DB + UI)
    suspend fun addCoffee(coffee: Coffee) {
        RetrofitClient.api.addCoffee(coffee)
        coffees.add(coffee)
    }

    // 🔥 UPDATE COFFEE (DB + UI)
    suspend fun updateCoffee(coffee: Coffee) {
        RetrofitClient.api.updateCoffee(coffee)
        val index = coffees.indexOfFirst { it.id == coffee.id }
        if (index != -1) coffees[index] = coffee
    }

    // 🔥 DELETE COFFEE (DB + UI)
    suspend fun deleteCoffee(coffee: Coffee) {
        RetrofitClient.api.deleteCoffee(coffee.id)
        coffees.remove(coffee)
    }
}
