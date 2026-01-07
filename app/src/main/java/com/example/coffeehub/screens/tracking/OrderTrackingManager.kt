package com.example.coffeehub.screens.tracking

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.*

object OrderTrackingManager {

    val steps = mutableStateListOf(
        StepModel(1, "Order Received", completed = true, active = false),
        StepModel(2, "Order Confirmed", completed = false, active = false),
        StepModel(3, "Preparing", completed = false, active = false),
        StepModel(4, "Ready to Serve", completed = false, active = false),
        StepModel(5, "Served", completed = false, active = false)
    )

    // ⏱ 10 minutes total
    val remainingSeconds = mutableStateOf(10 * 60)

    private var job: Job? = null
    private var started = false

    fun startTracking() {
        if (started) return
        started = true

        job = CoroutineScope(Dispatchers.Main).launch {

            while (remainingSeconds.value > 0) {

                delay(1_000)
                remainingSeconds.value--

                val elapsed = 600 - remainingSeconds.value // seconds passed

                when {
                    elapsed >= 420 -> markStep(4) // 7–10 min → Served
                    elapsed >= 360 -> markStep(3) // 6–7 min → Ready
                    elapsed >= 240 -> markStep(2) // 4–6 min → Preparing
                    elapsed >= 120 -> markStep(1) // 2–4 min → Confirmed
                }
            }

            // Ensure Served stays GREEN at 00:00
            markStep(4)
        }
    }

    private fun markStep(index: Int) {
        steps.forEachIndexed { i, step ->
            steps[i] = step.copy(
                completed = i <= index,
                active = i == index
            )
        }
    }

    fun reset() {
        job?.cancel()
        started = false
        remainingSeconds.value = 10 * 60

        steps.forEachIndexed { i, step ->
            steps[i] = step.copy(
                completed = i == 0,
                active = false
            )
        }
    }
}