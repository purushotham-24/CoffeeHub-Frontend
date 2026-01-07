package com.example.coffeehub.screens.tracking

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.*

object OrderTrackingManager {

    // Timeline steps (same as your UI)
    val steps = mutableStateListOf(
        StepModel(1, "Order Received", completed = true, active = false),
        StepModel(2, "Order Confirmed", completed = false, active = true),
        StepModel(3, "Preparing", completed = false, active = false),
        StepModel(4, "Ready to Serve", completed = false, active = false),
        StepModel(5, "Served", completed = false, active = false)
    )

    val isTrackingStarted = mutableStateOf(false)

    // ⏱ Simple countdown timer (10 minutes)
    val remainingSeconds = mutableStateOf(10 * 60)

    private var job: Job? = null

    fun startTracking() {
        if (isTrackingStarted.value) return
        isTrackingStarted.value = true

        job = CoroutineScope(Dispatchers.Main).launch {

            // 🔥 Simple countdown timer
            while (remainingSeconds.value > 0) {
                delay(1000)
                remainingSeconds.value--
            }

            // ⏱ Timer finished → Served becomes GREEN
            steps[steps.lastIndex] =
                steps.last().copy(completed = true, active = false)
        }
    }

    fun reset() {
        job?.cancel()
        isTrackingStarted.value = false
        remainingSeconds.value = 10 * 60

        steps.clear()
        steps.addAll(
            listOf(
                StepModel(1, "Order Received", completed = true, active = false),
                StepModel(2, "Order Confirmed", completed = false, active = true),
                StepModel(3, "Preparing", completed = false, active = false),
                StepModel(4, "Ready to Serve", completed = false, active = false),
                StepModel(5, "Served", completed = false, active = false)
            )
        )
    }
}
