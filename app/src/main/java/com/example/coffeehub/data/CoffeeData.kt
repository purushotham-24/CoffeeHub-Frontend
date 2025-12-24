package com.example.coffeehub.data

import com.example.coffeehub.model.Coffee
import com.example.coffeehub.screens.admin.AdminManager

/**
 * 🚨 IMPORTANT:
 * This file is NO LONGER static data.
 * It dynamically exposes AdminManager.coffees
 *
 * This means:
 * - User screens remain unchanged
 * - Admin edits instantly reflect everywhere
 */

/* ---------------- FILTER COFFEES ---------------- */
val filterCoffees: List<Coffee>
    get() = AdminManager.coffees.filter { it.category == "FILTER" }

/* ---------------- CAPPUCCINOS ---------------- */
val cappuccinos: List<Coffee>
    get() = AdminManager.coffees.filter { it.category == "CAPPUCCINO" }

/* ---------------- COLD COFFEES ---------------- */
val coldCoffees: List<Coffee>
    get() = AdminManager.coffees.filter { it.category == "COLD" }

/* ---------------- LATTES ---------------- */
val lattes: List<Coffee>
    get() = AdminManager.coffees.filter { it.category == "LATTE" }

/* ---------------- ALL COFFEES ---------------- */
val allCoffees: List<Coffee>
    get() = AdminManager.coffees

fun getCoffeeById(id: String): Coffee? =
    AdminManager.coffees.firstOrNull { it.id == id }

fun findFirstCoffeeMatching(query: String): Coffee? =
    AdminManager.coffees.firstOrNull {
        it.name.contains(query, ignoreCase = true)
    }

fun searchCoffees(query: String): List<Coffee> =
    AdminManager.coffees.filter {
        it.name.contains(query, ignoreCase = true)
    }
