package com.example.coffeehub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.coffeehub.navigation.AppNavHost
import com.example.coffeehub.utils.SessionManager

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ✅ RESTORE LOGIN SESSION
        val prefs = getSharedPreferences("coffeehub_prefs", MODE_PRIVATE)
        SessionManager.userId = prefs.getInt("user_id", -1)

        setContent {
            Surface(
                color = MaterialTheme.colorScheme.background
            ) {
                AppNavHost()
            }
        }
    }
}
