package com.simats.coffeehub.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.simats.coffeehub.data.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun Register(nav: NavController) {

    val scope = rememberCoroutineScope()

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var isLoading by remember { mutableStateOf(false) }
    var errorMsg by remember { mutableStateOf("") }

    val brown = Color(0xFF5C4033)
    val cream = Color(0xFFF5E6CF)

    val passwordPattern =
        Regex("^(?=.*[A-Z])(?=.*[0-9])(?=.*[@#\$%^&+=!]).{8,}$")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(cream, brown))),
        contentAlignment = Alignment.Center
    ) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(26.dp),
            shape = RoundedCornerShape(26.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {

            Column(
                modifier = Modifier.padding(28.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text("Create Account", fontSize = 28.sp, color = brown)

                Spacer(Modifier.height(24.dp))

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    placeholder = { Text("Full Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(14.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = { Text("Email Address") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(14.dp))

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

                Spacer(Modifier.height(26.dp))

                Button(
                    onClick = {
                        val cleanName = name.trim()
                        val cleanEmail = email.trim()

                        when {
                            cleanName.isBlank() ->
                                errorMsg = "Enter Name"

                            !Regex("^[A-Za-z]+( [A-Za-z]+)*$")
                                .matches(cleanName) ->
                                errorMsg = "Name must contain only letters"

                            !Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")
                                .matches(cleanEmail) ->
                                errorMsg = "Invalid Email"

                            !passwordPattern.matches(password) ->
                                errorMsg = "Weak Password (Use A-Z, 0-9 & symbol)"

                            else -> {
                                errorMsg = ""
                                isLoading = true

                                scope.launch(Dispatchers.IO) {
                                    try {
                                        val res = AuthRepository().register(
                                            name = cleanName,
                                            email = cleanEmail,
                                            password = password,
                                            phone = "",
                                            dob = ""
                                        )

                                        withContext(Dispatchers.Main) {
                                            if (res.status) {
                                                nav.navigate("login") {
                                                    popUpTo("register") { inclusive = true }
                                                }
                                            } else {
                                                errorMsg = res.message
                                            }
                                        }
                                    } catch (e: Exception) {
                                        withContext(Dispatchers.Main) {
                                            errorMsg = "Network error. Try again."
                                        }
                                    } finally {
                                        withContext(Dispatchers.Main) {
                                            isLoading = false
                                        }
                                    }
                                }
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    enabled = !isLoading,
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = brown)
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            color = Color.White,
                            strokeWidth = 2.dp,
                            modifier = Modifier.size(20.dp)
                        )
                    } else {
                        Text("Create Account", color = Color.White, fontSize = 17.sp)
                    }
                }

                Spacer(Modifier.height(16.dp))

                Row {
                    Text("Already have an account? ", color = Color.Gray)
                    Text(
                        "Login",
                        color = brown,
                        modifier = Modifier.clickable {
                            nav.navigate("login")
                        }
                    )
                }
            }
        }
    }
}
