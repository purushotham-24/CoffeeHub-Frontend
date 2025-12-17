package com.example.coffeehub.navigation
import com.example.coffeehub.screens.auth.ResetPasswordOtp

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

// ONBOARDING + AUTH
import com.example.coffeehub.screens.onboarding.Onboarding1
import com.example.coffeehub.screens.auth.Login
import com.example.coffeehub.screens.auth.Register
import com.example.coffeehub.screens.auth.ForgotPassword


// HOME
import com.example.coffeehub.screens.home.HomeScreen
import com.example.coffeehub.screens.home.NotificationSettings

// SEARCH
import com.example.coffeehub.screens.search.SearchScreen
import com.example.coffeehub.screens.search.FilterScreen
import com.example.coffeehub.screens.search.SearchResultsScreen
import com.example.coffeehub.screens.search.SearchSuggestionsScreen

// PROFILE
import com.example.coffeehub.screens.profile.ProfilePage
import com.example.coffeehub.screens.profile.EditProfileScreen

// SEAT BOOKING
import com.example.coffeehub.screens.booking.*
import com.example.coffeehub.screens.bookings.SeatBookingDetailsScreen

// WORKSPACE
import com.example.coffeehub.screens.booking.WorkspaceOptions
import com.example.coffeehub.screens.booking.WorkspaceDetails
import com.example.coffeehub.screens.booking.WorkspaceHourlyPricing

// OTHER
import com.example.coffeehub.screens.orders.OrderHistoryScreen
import com.example.coffeehub.screens.locations.NearbyLocationsScreen
import com.example.coffeehub.screens.tracking.OrderTrackingScreen
import com.example.coffeehub.screens.cart.CartScreen
import com.example.coffeehub.screens.cart.Promo
import com.example.coffeehub.screens.support.HelpSupportScreen
import com.example.coffeehub.coffee.screens.favorites.SavedFavoritesScreen

// PAYMENT
import com.example.coffeehub.screens.payment.PaymentOptionsScreen
import com.example.coffeehub.screens.payment.UPIPaymentScreen
import com.example.coffeehub.screens.payment.CardPaymentScreen
import com.example.coffeehub.screens.payment.PaymentSuccessScreen

// COFFEE FLOW
import com.example.coffeehub.screens.coffee.PopularCoffeeList
import com.example.coffeehub.screens.coffee.CoffeeDetails
import com.example.coffeehub.screens.coffee.CupSize
import com.example.coffeehub.screens.coffee.AddToCart
import com.example.coffeehub.screens.coffee.FilterCoffeeList
import com.example.coffeehub.screens.coffee.CappuccinoList
import com.example.coffeehub.screens.coffee.ColdCoffeeList
import com.example.coffeehub.screens.coffee.LatteList

// CROWD PREDICTION
import com.example.coffeehub.screens.prediction.CrowdPredictionScreen

@Composable
fun AppNavHost() {

    val nav = rememberNavController()

    NavHost(navController = nav, startDestination = "onboarding1") {

        // ONBOARDING
        composable("onboarding1") { Onboarding1(nav) }

        // AUTH
        composable("login") { Login(nav) }
        composable("register") { Register(nav) }
        composable("forgot-password") { ForgotPassword(nav) }
        composable("reset_password/{email}") { entry ->
            ResetPasswordOtp(
                nav = nav,
                email = entry.arguments?.getString("email") ?: ""
            )
        }

        // HOME
        composable("home") { HomeScreen(nav) }
        composable("notifications") { NotificationSettings { nav.popBackStack() } }

        // POPULAR COFFEE
        composable("popular_coffee") { PopularCoffeeList(nav) }

        // COFFEE FLOW
        composable("coffee_details/{id}") { entry ->
            CoffeeDetails(nav, entry.arguments?.getString("id") ?: "1")
        }
        composable("cup_size/{id}") { entry ->
            CupSize(nav, entry.arguments?.getString("id") ?: "1")
        }
        composable("add_to_cart") { AddToCart(nav) }

        // CATEGORY LISTS
        composable("filter_coffee") { FilterCoffeeList(nav) }
        composable("cappuccino_list") { CappuccinoList(nav) }
        composable("cold_coffee_list") { ColdCoffeeList(nav) }
        composable("latte_list") { LatteList(nav) }

        // SEARCH
        composable("search") { SearchScreen(nav) }
        composable("search-results") { SearchResultsScreen(nav) }
        composable("search-filter") { FilterScreen(nav) }
        composable("search-suggestions") { SearchSuggestionsScreen(nav) }

        // PROFILE
        composable("profile") { ProfilePage(nav) }
        composable("edit-profile") { EditProfileScreen(nav) }
        composable("booking-details") { SeatBookingDetailsScreen(nav) }

        // BOOKING
        composable("seat_map") { SeatLayoutMap(nav) }
        composable("datetime") { DateTimeSelection(nav) }
        composable("booking_confirmation") { BookingConfirmation(nav) }
        composable("booking_success") { BookingSuccess(nav) }

        // WORKSPACE
        composable("workspace_options") { WorkspaceOptions(nav) }
        composable("workspace_details/{id}") { entry ->
            WorkspaceDetails(nav, entry.arguments?.getString("id") ?: "1")
        }
        composable("workspace_pricing/{id}") { entry ->
            WorkspaceHourlyPricing(nav, entry.arguments?.getString("id") ?: "1")
        }

        // PAYMENT
        composable("payment_options") { PaymentOptionsScreen(nav) }
        composable("payment_upi") { UPIPaymentScreen(nav) }
        composable("payment_card") { CardPaymentScreen(nav) }
        composable("payment_success") { PaymentSuccessScreen(nav) }

        // OTHER
        composable("order-history") { OrderHistoryScreen(nav) }
        composable("nearby") { NearbyLocationsScreen(nav) }
        composable("tracking") { OrderTrackingScreen(nav) }
        composable("cart") { CartScreen(nav) }
        composable("promo") { Promo(nav) }
        composable("favorites") { SavedFavoritesScreen(nav) }
        composable("support") { HelpSupportScreen(nav) }

        // CROWD PREDICTION (FIXED)
        composable("crowd_prediction") {
            CrowdPredictionScreen(nav)
        }

    }
}
