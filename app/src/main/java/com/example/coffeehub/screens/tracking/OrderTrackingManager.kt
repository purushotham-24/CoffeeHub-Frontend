package com.example.coffeehub.screens.tracking

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.*

object OrderTrackingManager {

    // 🧾 ORDER TRACKING STEPS
    val steps = mutableStateListOf(
        StepModel(1, "Order Received", completed = true, active = true),
        StepModel(2, "Order Confirmed", completed = false, active = false),
        StepModel(3, "Preparing", completed = false, active = false),
        StepModel(4, "Ready to Serve", completed = false, active = false),
        StepModel(5, "Served", completed = false, active = false)
    )

    // ⏱ TOTAL TIME = 10 MINUTES (600 seconds)
    val remainingSeconds = mutableStateOf(10 * 60)

    private var job: Job? = null
    private var started = false

    // ▶️ START TRACKING
    fun startTracking() {
        if (started) return
        started = true

        job = CoroutineScope(Dispatchers.Main).launch {

            while (remainingSeconds.value > 0) {
                delay(1_000)
                remainingSeconds.value--

                val elapsed = 600 - remainingSeconds.value

                when {
                    elapsed >= 480 -> markStep(4) // Served (last 2 min)
                    elapsed >= 360 -> markStep(3) // Ready to Serve
                    elapsed >= 240 -> markStep(2) // Preparing
                    elapsed >= 120 -> markStep(1) // Order Confirmed
                }
            }

            // ✅ ENSURE SERVED IS COMPLETED AT 00:00
            markStep(4)
        }
    }

    // 🔄 UPDATE STEP STATES
    private fun markStep(index: Int) {
        steps.forEachIndexed { i, step ->
            steps[i] = step.copy(
                completed = i <= index,
                active = i == index
            )
        }
    }

    // 🔁 RESET TRACKING
    fun reset() {
        job?.cancel()
        started = false
        remainingSeconds.value = 10 * 60

        steps.forEachIndexed { i, step ->
            steps[i] = step.copy(
                completed = i == 0,
                active = i == 0
            )
        }
    }
}
