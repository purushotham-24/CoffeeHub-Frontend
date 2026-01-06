package com.example.coffeehub.screens.booking

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.coffeehub.screens.home.components.HeaderBar
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateTimeSelection(nav: NavController) {

    val today = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    var selectedDate by remember { mutableStateOf(today.format(formatter)) }
    var selectedTime by remember { mutableStateOf("") }
    var showCalendar by remember { mutableStateOf(false) }

    val isSeatBooking = BookingManager.bookingType.value == "seat"

    // 🕒 Cafe hours (7 AM – 11 PM)
    val startHour = 7
    val endHour = 23
    val calendar = Calendar.getInstance()
    val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
    val currentMinute = calendar.get(Calendar.MINUTE)

    val totalSeats = SeatManager.seats.size

    /* ---------------- TIME SLOTS ---------------- */
    val timeSlots = (startHour until endHour).map { hour ->
        val displayHour = if (hour % 12 == 0) 12 else hour % 12
        val period = if (hour < 12) "AM" else "PM"
        String.format("%02d:00 %s", displayHour, period)
    }

    /* ---------------- DATE PICKER ---------------- */
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = today
            .atStartOfDay(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli(),
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                val pickedDate = Instant
                    .ofEpochMilli(utcTimeMillis)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()
                return !pickedDate.isBefore(today)
            }
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        HeaderBar(title = "Select Date & Time", nav = nav, showBack = true)

        Column(Modifier.padding(20.dp)) {

            /* ---------------- DATE ---------------- */
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.CalendarToday, null, tint = Color(0xFF5C4033))
                Spacer(Modifier.width(8.dp))
                Text("Select Date", fontWeight = FontWeight.SemiBold)
            }

            Spacer(Modifier.height(10.dp))

            OutlinedTextField(
                value = selectedDate,
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showCalendar = true },
                enabled = false,
                shape = RoundedCornerShape(18.dp),
                trailingIcon = {
                    Icon(Icons.Default.CalendarToday, null, tint = Color(0xFF5C4033))
                }
            )

            Spacer(Modifier.height(24.dp))

            /* ---------------- TIME ---------------- */
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Schedule, null, tint = Color(0xFF5C4033))
                Spacer(Modifier.width(8.dp))
                Text("Select Time", fontWeight = FontWeight.SemiBold)
            }

            Spacer(Modifier.height(12.dp))

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                timeSlots.chunked(3).forEachIndexed { rowIndex, row ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        row.forEachIndexed { colIndex, time ->

                            val hour = startHour + (rowIndex * 3 + colIndex)

                            /* ❌ Past hour */
                            val isPastHour =
                                selectedDate == today.format(formatter) &&
                                        hour < currentHour

                            /* ❌ Past 15 minutes of current hour */
                            val isPast15Mins =
                                selectedDate == today.format(formatter) &&
                                        hour == currentHour &&
                                        currentMinute > 15

                            /* 🪑 Seat booking full */
                            val bookedSeatCount =
                                if (isSeatBooking)
                                    BookingHistoryManager.bookings
                                        .filter {
                                            it.date == selectedDate && it.time == time
                                        }
                                        .sumOf {
                                            it.title
                                                .removePrefix("Seats:")
                                                .split(",")
                                                .count { seat -> seat.trim().isNotEmpty() }
                                        }
                                else 0

                            val isFullyBooked =
                                isSeatBooking && bookedSeatCount >= totalSeats

                            val enabled = !isPastHour && !isPast15Mins && !isFullyBooked

                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(48.dp)
                                    .background(
                                        when {
                                            !enabled -> Color(0xFFE5E5E5)
                                            selectedTime == time -> Color(0xFF5C4033)
                                            else -> Color(0xFFF5E6CF)
                                        },
                                        RoundedCornerShape(14.dp)
                                    )
                                    .clickable(enabled = enabled) {
                                        selectedTime = time
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = time,
                                    color = when {
                                        !enabled -> Color.Gray
                                        selectedTime == time -> Color.White
                                        else -> Color(0xFF5C4033)
                                    },
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(Modifier.weight(1f))

        Button(
            onClick = {
                BookingManager.selectedDate.value = selectedDate
                BookingManager.selectedTime.value = selectedTime
                nav.navigate("booking_confirmation")
            },
            enabled = selectedTime.isNotEmpty(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .height(56.dp),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(Color(0xFF5C4033))
        ) {
            Text(
                "Continue",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }

    /* ---------------- DATE PICKER ---------------- */
    if (showCalendar) {
        DatePickerDialog(
            onDismissRequest = { showCalendar = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val pickedDate = Instant
                            .ofEpochMilli(millis)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                        selectedDate = pickedDate.format(formatter)
                    }
                    selectedTime = ""
                    showCalendar = false
                }) {
                    Text("OK")
                }
            }
        ) {
            DatePicker(state = datePickerState, showModeToggle = false)
        }
    }
}
