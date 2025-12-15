package com.example.coffeehub.data

import com.example.coffeehub.model.Coffee

// ---------------- FILTER COFFEES (10) ----------------
val filterCoffees = listOf(
    Coffee("f1", "Classic Filter Coffee", "Traditional South Indian filter coffee", 120, 4.7,
        "https://images.unsplash.com/photo-1541167760496-1628856ab772?w=400"),
    Coffee("f2", "Premium Filter", "Made with premium beans", 150, 4.8,
        "https://images.unsplash.com/photo-1509042239860-f550ce710b93?w=400"),
    Coffee("f3", "Mysore Filter", "Authentic Mysore blend", 140, 4.6,
        "https://images.unsplash.com/photo-1514432324607-a09d9b4aefdd?w=400"),
    Coffee("f4", "Strong Decoction", "Extra strong traditional coffee", 130, 4.8,
        "https://images.unsplash.com/photo-1495474472287-4d71bcdd2085?w=400"),
    Coffee("f5", "Organic Filter Coffee", "100% organic beans", 160, 4.7,
        "https://images.unsplash.com/photo-1509785307050-d4066910ec1e?w=400"),
    Coffee("f6", "House Special Filter", "Cafe house blend", 155, 4.9,
        "https://images.unsplash.com/photo-1559925393-8be0ec4767c8?w=400"),
    Coffee("f7", "Traditional Brass Brew", "Brewed in brass filter", 145, 4.6,
        "https://images.unsplash.com/photo-1522992319-0365e5f11656?w=400"),
    Coffee("f8", "Madras Filter Coffee", "Classic Madras taste", 135, 4.7,
        "https://images.unsplash.com/photo-1511920170033-f8396924c348?w=400"),
    Coffee("f9", "Pure Arabica Filter", "Arabica beans only", 170, 4.8,
        "https://images.unsplash.com/photo-1498804103079-a6351b050096?w=400"),
    Coffee("f10", "South Indian Special", "Authentic south style", 150, 4.9,
        "https://images.unsplash.com/photo-1509042239860-f550ce710b93?w=400")
)

// ---------------- CAPPUCCINOS (10) ----------------
val cappuccinos = listOf(
    Coffee("c1", "Classic Cappuccino", "Espresso with milk foam", 150, 4.9,
        "https://images.unsplash.com/photo-1572442388796-11668a67e53d?w=400"),
    Coffee("c2", "Vanilla Cappuccino", "Vanilla flavored", 170, 4.7,
        "https://images.unsplash.com/photo-1534778101976-62847782c213?w=400"),
    Coffee("c3", "Caramel Cappuccino", "Sweet caramel touch", 180, 4.8,
        "https://images.unsplash.com/photo-1545665225-b23b99e4d45e?w=400"),
    Coffee("c4", "Hazelnut Cappuccino", "Nutty flavor", 190, 4.9,
        "https://images.unsplash.com/photo-1511920170033-f8396924c348?w=400"),
    Coffee("c5", "Chocolate Cappuccino", "Choco infused", 200, 4.8,
        "https://images.unsplash.com/photo-1514432324607-a09d9b4aefdd?w=400"),
    Coffee("c6", "Creamy Cappuccino", "Extra creamy milk", 175, 4.7,
        "https://images.unsplash.com/photo-1509785307050-d4066910ec1e?w=400"),
    Coffee("c7", "Italian Cappuccino", "Italian style brew", 185, 4.9,
        "https://images.unsplash.com/photo-1495474472287-4d71bcdd2085?w=400"),
    Coffee("c8", "Double Shot Cappuccino", "Strong espresso base", 195, 4.8,
        "https://images.unsplash.com/photo-1559925393-8be0ec4767c8?w=400"),
    Coffee("c9", "Cinnamon Cappuccino", "Cinnamon flavor", 170, 4.6,
        "https://images.unsplash.com/photo-1498804103079-a6351b050096?w=400"),
    Coffee("c10", "Signature Cappuccino", "House special", 210, 4.9,
        "https://images.unsplash.com/photo-1509042239860-f550ce710b93?w=400")
)

// ---------------- COLD COFFEES (10) ----------------
val coldCoffees = listOf(
    Coffee("cc1", "Iced Latte", "Cold coffee with milk", 160, 4.8,
        "https://images.unsplash.com/photo-1517487881594-2787fef5ebf7?w=400"),
    Coffee("cc2", "Cold Brew", "Smooth cold brew", 180, 4.9,
        "https://images.unsplash.com/photo-1461023058943-07fcbe16d735?w=400"),
    Coffee("cc3", "Frappe", "Blended iced coffee", 200, 4.7,
        "https://images.unsplash.com/photo-1551030173-122aabc4489c?w=400"),
    Coffee("cc4", "Mocha Frappe", "Chocolate blend", 210, 4.8,
        "https://images.unsplash.com/photo-1509042239860-f550ce710b93?w=400"),
    Coffee("cc5", "Caramel Cold Coffee", "Sweet caramel", 190, 4.7,
        "https://images.unsplash.com/photo-1541167760496-1628856ab772?w=400"),
    Coffee("cc6", "Vanilla Iced Coffee", "Vanilla flavor", 185, 4.6,
        "https://images.unsplash.com/photo-1522992319-0365e5f11656?w=400"),
    Coffee("cc7", "Hazelnut Cold Brew", "Nutty cold brew", 195, 4.8,
        "https://images.unsplash.com/photo-1511920170033-f8396924c348?w=400"),
    Coffee("cc8", "Irish Cold Coffee", "Irish cream touch", 220, 4.9,
        "https://images.unsplash.com/photo-1495474472287-4d71bcdd2085?w=400"),
    Coffee("cc9", "Choco Iced Latte", "Chocolate iced latte", 200, 4.7,
        "https://images.unsplash.com/photo-1559925393-8be0ec4767c8?w=400"),
    Coffee("cc10", "Signature Cold Brew", "House cold brew", 230, 4.9,
        "https://images.unsplash.com/photo-1509785307050-d4066910ec1e?w=400")
)

// ---------------- LATTES (10) ----------------
val lattes = listOf(
    Coffee("l1", "Caffe Latte", "Espresso with steamed milk", 140, 4.8,
        "https://images.unsplash.com/photo-1561882468-9110e03e0f78?w=400"),
    Coffee("l2", "Vanilla Latte", "Vanilla flavored", 160, 4.7,
        "https://images.unsplash.com/photo-1570968915860-54d5c301fa9f?w=400"),
    Coffee("l3", "Caramel Latte", "Caramel sweetness", 170, 4.9,
        "https://images.unsplash.com/photo-1599639957043-f3aa5c986398?w=400"),
    Coffee("l4", "Hazelnut Latte", "Nutty latte", 175, 4.8,
        "https://images.unsplash.com/photo-1511920170033-f8396924c348?w=400"),
    Coffee("l5", "Mocha Latte", "Chocolate espresso", 180, 4.7,
        "https://images.unsplash.com/photo-1509042239860-f550ce710b93?w=400"),
    Coffee("l6", "Cinnamon Latte", "Warm cinnamon", 165, 4.6,
        "https://images.unsplash.com/photo-1498804103079-a6351b050096?w=400"),
    Coffee("l7", "Honey Latte", "Honey sweetness", 170, 4.8,
        "https://images.unsplash.com/photo-1541167760496-1628856ab772?w=400"),
    Coffee("l8", "Irish Latte", "Irish cream", 190, 4.9,
        "https://images.unsplash.com/photo-1495474472287-4d71bcdd2085?w=400"),
    Coffee("l9", "Almond Latte", "Almond milk latte", 185, 4.7,
        "https://images.unsplash.com/photo-1559925393-8be0ec4767c8?w=400"),
    Coffee("l10", "Signature Latte", "House special latte", 200, 4.9,
        "https://images.unsplash.com/photo-1509785307050-d4066910ec1e?w=400")
)

// ---------------- ALL COFFEES (PUBLIC) ----------------
val allCoffees = filterCoffees + cappuccinos + coldCoffees + lattes

fun getCoffeeById(id: String): Coffee? =
    allCoffees.firstOrNull { it.id == id }

fun findFirstCoffeeMatching(query: String): Coffee? =
    allCoffees.firstOrNull { it.name.contains(query, ignoreCase = true) }

fun searchCoffees(query: String): List<Coffee> =
    allCoffees.filter { it.name.contains(query, ignoreCase = true) }
