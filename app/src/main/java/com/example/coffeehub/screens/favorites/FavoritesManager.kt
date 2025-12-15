package com.example.coffeehub.coffee.screens.favorites

import android.content.Context

object FavoritesManager {

    private const val PREFS = "favorites_prefs"
    private const val KEY = "favorite_ids"

    fun getFavorites(context: Context): MutableSet<String> {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        return prefs.getStringSet(KEY, mutableSetOf()) ?: mutableSetOf()
    }

    fun isFavorite(context: Context, coffeeId: String): Boolean {
        return getFavorites(context).contains(coffeeId)
    }

    fun toggleFavorite(context: Context, coffeeId: String) {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        val favs = getFavorites(context)

        if (favs.contains(coffeeId)) {
            favs.remove(coffeeId)
        } else {
            favs.add(coffeeId)
        }

        prefs.edit().putStringSet(KEY, favs).apply()
    }
}
