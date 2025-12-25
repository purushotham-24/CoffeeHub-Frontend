package com.example.coffeehub.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.coffeehub.screens.admin.AdminDashboard
import com.example.coffeehub.screens.admin.AdminManager

// AUTH
import com.example.coffeehub.screens.onboarding.Onboarding1
import com.example.coffeehub.screens.auth.Login
import com.example.coffeehub.screens.auth.Register
import com.example.coffeehub.screens.auth.ForgotPassword
import com.example.coffeehub.screens.auth.ResetPasswordOtp

// HOME
import com.example.coffeehub.screens.home.HomeScreen
import com.example.coffeehub.screens.home.NotificationSettings

// SEARCH
import com.example.coffeehub.screens.search.*

// PROFILE
import com.example.coffeehub.screens.profile.*

// BOOKING
import com.example.coffeehub.screens.booking.*
import com.example.coffeehub.screens.bookings.SeatBookingDetailsScreen

// WORKSPACE
import com.example.coffeehub.screens.booking.WorkspaceOptions
import com.example.coffeehub.screens.booking.WorkspaceDetails
import com.example.coffeehub.screens.booking.WorkspaceHourlyPricing

// PAYMENT
import com.example.coffeehub.screens.payment.*

// COFFEE
import com.example.coffeehub.screens.coffee.*

// OTHER
import com.example.coffeehub.screens.orders.OrderHistoryScreen
import com.example.coffeehub.screens.locations.NearbyLocationsScreen
import com.example.coffeehub.screens.tracking.OrderTrackingScreen
import com.example.coffeehub.screens.cart.*
import com.example.coffeehub.screens.support.HelpSupportScreen
import com.example.coffeehub.coffee.screens.favorites.SavedFavoritesScreen
import com.example.coffeehub.screens.prediction.CrowdPredictionScreen

@Composable
fun AppNavHost() {

    val nav = rememberNavController()

    // 🔥 LOAD COFFEE DATA ON APP START (USER OR ADMIN)
    LaunchedEffect(Unit) {
        AdminManager.loadFromServer()
    }

    NavHost(navController = nav, startDestination = "onboarding1") {

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

        // COFFEE
        composable("popular_coffee") { PopularCoffeeList(nav) }
        composable("filter_coffee") { FilterCoffeeList(nav) }
        composable("cappuccino_list") { CappuccinoList(nav) }
        composable("cold_coffee_list") { ColdCoffeeList(nav) }
        composable("latte_list") { LatteList(nav) }

        composable("coffee_details/{id}") { entry ->
            CoffeeDetails(nav, entry.arguments?.getString("id") ?: "")
        }
        composable("cup_size/{id}") { entry ->
            CupSize(nav, entry.arguments?.getString("id") ?: "")
        }
        composable("add_to_cart") { AddToCart(nav) }

        // SEARCH
        composable("search") { SearchScreen(nav) }
        composable("search-results") { SearchResultsScreen(nav) }
        composable("search-filter") { FilterScreen(nav) }
        composable("search-suggestions") { SearchSuggestionsScreen(nav) }

        // PROFILE
        composable("profile") { ProfilePage(nav) }
        composable("edit-profile") { EditProfileScreen(nav) }

        // BOOKINGS
        composable("booking-details") { SeatBookingDetailsScreen(nav) }
        composable("seat_map") { SeatLayoutMap(nav) }
        composable("datetime") { DateTimeSelection(nav) }
        composable("booking_confirmation") { BookingConfirmation(nav) }
        composable("booking_success") { BookingSuccess(nav) }

        // WORKSPACE
        composable("workspace_options") { WorkspaceOptions(nav) }
        composable("workspace_details/{id}") { entry ->
            WorkspaceDetails(nav, entry.arguments?.getString("id") ?: "")
        }
        composable("workspace_pricing/{id}") { entry ->
            WorkspaceHourlyPricing(nav, entry.arguments?.getString("id") ?: "")
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

        // CROWD
        composable("crowd_prediction") { CrowdPredictionScreen(nav) }

        // ADMIN
        composable("admin_home") { AdminDashboard(nav) }
    }
}
