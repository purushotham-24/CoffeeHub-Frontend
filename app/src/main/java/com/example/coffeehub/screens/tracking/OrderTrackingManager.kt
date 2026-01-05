package com.example.coffeehub.screens.tracking

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.*

object OrderTrackingManager {

    val steps = mutableStateListOf<StepModel>()
    val isTrackingStarted = mutableStateOf(false)

    private var job: Job? = null

    fun startTracking(intervalMillis: Long = 3 * 60 * 1000) {
        if (isTrackingStarted.value) return
        isTrackingStarted.value = true

        job = CoroutineScope(Dispatchers.Main).launch {

            // Order Received → Completed immediately
            updateStep(activeIndex = 1)

            for (i in 2 until steps.size) {
                delay(intervalMillis)
                updateStep(activeIndex = i)
            }
        }
    }

    private fun updateStep(activeIndex: Int) {
        steps.forEachIndexed { index, step ->
            steps[index] = step.copy(
                completed = index < activeIndex,
                active = index == activeIndex
            )
        }
    }

    fun reset() {
        job?.cancel()
        job = null
        isTrackingStarted.value = false

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