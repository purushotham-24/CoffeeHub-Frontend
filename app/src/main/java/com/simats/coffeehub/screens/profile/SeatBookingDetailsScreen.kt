package com.simats.coffeehub.screens.bookings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.simats.coffeehub.screens.booking.BookingHistory
import com.simats.coffeehub.screens.booking.BookingHistoryManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeatBookingDetailsScreen(nav: NavController) {

    val brown = Color(0xFF5C4033)

    var showCancelDialog by remember { mutableStateOf(false) }
    var selectedBooking by remember { mutableStateOf<BookingHistory?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "My Bookings",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    // ✅ UPDATED BACK BUTTON (ALWAYS GO HOME)
                    IconButton(
                        onClick = {
                            nav.navigate("home") {
                                popUpTo("home") { inclusive = true }
                                launchSingleTop = true
                            }
                        }
                    ) {
                        Icon(Icons.Default.ArrowBack, null, tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = brown)
            )
        }
    ) { padding ->

        if (BookingHistoryManager.bookings.isEmpty()) {
            Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Text("No bookings yet", fontSize = 18.sp, color = Color.Gray)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .background(Color.White),
                contentPadding = PaddingValues(bottom = 24.dp)
            ) {
                items(
                    items = BookingHistoryManager.bookings,
                    key = { it.id }
                ) { booking ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 10.dp),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(Color(0xFFF5E6CF))
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {

                            Text(
                                text = if (booking.type == "seat")
                                    "Seat Reservation"
                                else
                                    "Workspace Reservation",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = brown
                            )

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    Icons.Default.LocationOn,
                                    null,
                                    tint = brown,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(Modifier.width(8.dp))
                                Text(booking.title, fontSize = 14.sp, color = Color.DarkGray)
                            }

                            Divider(color = brown.copy(alpha = 0.25f))

                            BookingInfoRow(Icons.Default.CalendarToday, "Date", booking.date)
                            BookingInfoRow(Icons.Default.Schedule, "Time", booking.time)

                            Text(
                                "Free cancellation before booking time · ₹0 charges",
                                fontSize = 12.sp,
                                color = Color(0xFF2E7D32)
                            )

                            OutlinedButton(
                                onClick = {
                                    selectedBooking = booking
                                    showCancelDialog = true
                                },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(50),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    contentColor = Color.Red
                                )
                            ) {
                                Icon(Icons.Default.Cancel, null, modifier = Modifier.size(18.dp))
                                Spacer(Modifier.width(8.dp))
                                Text("Cancel Booking")
                            }
                        }
                    }
                }
            }
        }
    }

    // ---------- CANCEL CONFIRMATION ----------
    if (showCancelDialog && selectedBooking != null) {
        AlertDialog(
            onDismissRequest = { showCancelDialog = false },
            title = { Text("Cancel Booking?") },
            text = {
                Text(
                    "You can cancel this booking now with ₹0 charges.\n\n" +
                            "This action cannot be undone."
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (selectedBooking!!.type == "seat") {
                            val seatIds = selectedBooking!!.title
                                .replace("Seats:", "")
                                .split(",")
                                .map { it.trim() }

                            // Seats are auto-released based on time slot

                        }

                        BookingHistoryManager.bookings.remove(selectedBooking)

                        selectedBooking = null
                        showCancelDialog = false
                    }
                ) {
                    Text("Yes, Cancel", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        selectedBooking = null
                        showCancelDialog = false
                    }
                ) {
                    Text("No")
                }
            }
        )
    }
}

/* ---------- INFO ROW ---------- */
@Composable
fun BookingInfoRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, null, tint = Color(0xFF5C4033), modifier = Modifier.size(18.dp))
        Spacer(Modifier.width(10.dp))
        Column {
            Text(label, fontSize = 12.sp, color = Color.Gray)
            Text(value, fontSize = 14.sp, fontWeight = FontWeight.Medium)
        }
    }
}
