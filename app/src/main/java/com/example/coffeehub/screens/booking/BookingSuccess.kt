package com.example.coffeehub.screens.booking

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.coffeehub.data.repository.BookingRepository
import com.example.coffeehub.screens.home.NotificationManager
import com.example.coffeehub.utils.SessionManager
import kotlinx.coroutines.launch

@Composable
fun BookingSuccess(nav: NavController) {

    val brown = Color(0xFF5C4033)
    val isSeatBooking = BookingManager.bookingType.value == "seat"

    val repo = remember { BookingRepository() }
    val scope = rememberCoroutineScope()

    // ✅ BACKEND CALL PROTECTION (ONLY ADDITION)
    var apiCalled by remember { mutableStateOf(false) }

    /* 🔐 SAVE BOOKING (LOCAL + BACKEND) */
    LaunchedEffect(Unit) {

        if (apiCalled) return@LaunchedEffect
        apiCalled = true

        // 🚨 USER MUST BE LOGGED IN
        if (SessionManager.userId <= 0) {
            println("❌ INVALID USER ID: ${SessionManager.userId}")
            return@LaunchedEffect
        }

        if (
            BookingManager.selectedDate.value.isBlank() ||
            BookingManager.selectedTime.value.isBlank()
        ) return@LaunchedEffect

        if (isSeatBooking && BookingManager.selectedSeats.value.isEmpty()) return@LaunchedEffect

        if (!isSeatBooking &&
            BookingManager.workspaceName.value.isNullOrBlank()
        ) return@LaunchedEffect

        // 🔒 LOCK SEATS (LOCAL)
        if (isSeatBooking) {
            SeatManager.occupySeats(BookingManager.selectedSeats.value)
        }

        val bookingTitle = if (isSeatBooking)
            "Seats: ${BookingManager.selectedSeats.value.joinToString(", ")}"
        else
            BookingManager.workspaceName.value!!

        val alreadyExists = BookingHistoryManager.bookings.any {
            it.type == BookingManager.bookingType.value &&
                    it.title == bookingTitle &&
                    it.date == BookingManager.selectedDate.value &&
                    it.time == BookingManager.selectedTime.value
        }

        if (!alreadyExists) {

            // ✅ SAVE LOCALLY
            BookingHistoryManager.bookings.add(
                BookingHistory(
                    type = BookingManager.bookingType.value,
                    title = bookingTitle,
                    date = BookingManager.selectedDate.value,
                    time = BookingManager.selectedTime.value
                )
            )

            // ✅ SAVE TO BACKEND (UNCHANGED LOGIC)
            scope.launch {
                try {
                    val res = repo.placeBooking(
                        userId = SessionManager.userId,
                        type = BookingManager.bookingType.value,
                        title = bookingTitle,
                        date = BookingManager.selectedDate.value,
                        time = BookingManager.selectedTime.value,
                        seats = if (isSeatBooking)
                            BookingManager.selectedSeats.value
                        else
                            emptyList()
                    )

                    println("✅ BOOKING BACKEND RESPONSE: $res")

                } catch (e: Exception) {
                    println("❌ BOOKING API ERROR")
                    e.printStackTrace()
                }
            }

            // 🔔 NOTIFICATION
            NotificationManager.addBookingNotification(
                title = if (isSeatBooking)
                    "Seat Booking Confirmed"
                else
                    "Workspace Booking Confirmed",
                message = "Scheduled on ${BookingManager.selectedDate.value} at ${BookingManager.selectedTime.value}"
            )
        }
    }

    /* ================= UI (UNCHANGED) ================= */

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Box(
            modifier = Modifier
                .size(96.dp)
                .background(Color(0xFFB7E9B0), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.CheckCircle,
                contentDescription = null,
                tint = Color(0xFF2E7D32),
                modifier = Modifier.size(64.dp)
            )
        }

        Spacer(Modifier.height(20.dp))

        Text("Booking Confirmed!", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = brown)

        Spacer(Modifier.height(6.dp))

        Text(
            "Your reservation is successfully completed 🎉",
            fontSize = 14.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(26.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(Color(0xFFF5E6CF))
        ) {
            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                Text("Booking Details", fontSize = 17.sp, fontWeight = FontWeight.SemiBold, color = brown)

                DetailRow(
                    Icons.Default.LocationOn,
                    if (isSeatBooking) "Seats" else "Workspace",
                    if (isSeatBooking)
                        BookingManager.selectedSeats.value.joinToString(", ")
                    else
                        BookingManager.workspaceName.value ?: ""
                )

                DetailRow(Icons.Default.CalendarMonth, "Date", BookingManager.selectedDate.value)
                DetailRow(Icons.Default.Schedule, "Time", BookingManager.selectedTime.value)
            }
        }

        Spacer(Modifier.height(28.dp))

        Button(
            onClick = {
                BookingManager.clear()
                nav.navigate("booking-details")
            },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(brown)
        ) {
            Text("View My Bookings", color = Color.White)
        }

        Spacer(Modifier.height(12.dp))

        OutlinedButton(
            onClick = {
                BookingManager.clear()
                nav.navigate("home") {
                    popUpTo("home") { inclusive = true }
                }
            },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(50)
        ) {
            Text("Back to Home", color = brown)
        }
    }
}

/* ---------- REUSABLE ROW (UNCHANGED) ---------- */
@Composable
fun DetailRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, null, tint = Color(0xFF5C4033))
        Spacer(Modifier.width(10.dp))
        Column {
            Text(label, fontSize = 12.sp, color = Color.Gray)
            Text(value, fontWeight = FontWeight.Medium)
        }
    }
}
