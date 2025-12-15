package com.example.coffeehub.screens.auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.*

@Composable
fun Register(nav: NavController) {

    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMsg by remember { mutableStateOf("") }

    val brown = Color(0xFF5C4033)
    val cream = Color(0xFFF5E6CF)

    // Strong password rule
    val passwordPattern = Regex("^(?=.*[A-Z])(?=.*[0-9])(?=.*[@#\$%^&+=!]).{8,}$")

    Column(
        Modifier
            .fillMaxSize()
            .padding(26.dp)
    ) {

        Text("Create Account", fontSize = 30.sp, fontWeight = FontWeight.Bold, color = brown)
        Spacer(Modifier.height(30.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            placeholder = { Text("Full Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(15.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            placeholder = { Text("Email Address") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(15.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = { Text("Create Password") },
            modifier = Modifier.fillMaxWidth()
        )

        if (errorMsg.isNotEmpty()) {
            Spacer(Modifier.height(10.dp))
            Text(errorMsg, color = Color.Red, fontSize = 13.sp)
        }

        Spacer(Modifier.height(28.dp))

        Button(
            onClick = {

                // VALIDATION
                when {
                    name.isBlank() ->
                        errorMsg = "Enter Name"

                    !email.contains("@") ->
                        errorMsg = "Invalid Email"

                    !passwordPattern.matches(password) ->
                        errorMsg = "Weak Password (Must include A-Z, 0-9 & symbol)"

                    else -> {
                        errorMsg = ""
                        isLoading = true

                        // Simulate registration API (offline)
                        CoroutineScope(Dispatchers.Main).launch {
                            delay(1200)
                            isLoading = false

                            Toast.makeText(context, "Account Created!", Toast.LENGTH_SHORT).show()

                            // Navigate to Login
                            nav.navigate("login") {
                                popUpTo("register") { inclusive = true }
                            }
                        }
                    }
                }

            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            enabled = !isLoading,
            colors = ButtonDefaults.buttonColors(brown)
        ) {
            if (isLoading) {
                CircularProgressIndicator(color = Color.White, strokeWidth = 2.dp)
            } else {
                Text("Create Account", color = Color.White, fontSize = 17.sp)
            }
        }

        Spacer(Modifier.height(18.dp))

        Row {
            Text("Already have an account? ", fontSize = 15.sp, color = Color.Gray)
            Text(
                "Login",
                color = brown,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable {
                    nav.navigate("login")
                }
            )
        }
    }
}