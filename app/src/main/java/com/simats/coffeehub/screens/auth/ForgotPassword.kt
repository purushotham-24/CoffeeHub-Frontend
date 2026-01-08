@file:OptIn(ExperimentalMaterial3Api::class)

package com.simats.coffeehub.screens.auth

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.simats.coffeehub.data.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun ForgotPassword(nav: NavController) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var email by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMsg by remember { mutableStateOf("") }

    val brown = Color(0xFF5C4033)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Forgot Password", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { nav.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, null, tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = brown)
            )
        }
    ) { pad ->

        Column(
            modifier = Modifier
                .padding(pad)
                .padding(24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Enter your registered email to receive OTP",
                fontSize = 14.sp,
                color = Color.Gray
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                leadingIcon = {
                    Icon(Icons.Default.Email, null)
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            if (errorMsg.isNotEmpty()) {
                Text(errorMsg, color = Color.Red, fontSize = 13.sp)
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                colors = ButtonDefaults.buttonColors(containerColor = brown),
                enabled = !isLoading,
                onClick = {
                    val cleanEmail = email.trim()

                    if (cleanEmail.isBlank()) {
                        errorMsg = "Email required"
                        return@Button
                    }

                    isLoading = true
                    errorMsg = ""

                    scope.launch(Dispatchers.IO) {
                        try {
                            val res =
                                AuthRepository().forgotPassword(cleanEmail)

                            withContext(Dispatchers.Main) {
                                if (res.status) {
                                    Toast.makeText(
                                        context,
                                        "OTP sent to your email",
                                        Toast.LENGTH_LONG
                                    ).show()

                                    nav.navigate("reset_password/$cleanEmail")
                                } else {
                                    errorMsg = res.message
                                }
                            }
                        } catch (e: Exception) {
                            withContext(Dispatchers.Main) {
                                errorMsg = "Network error"
                            }
                        } finally {
                            withContext(Dispatchers.Main) {
                                isLoading = false
                            }
                        }
                    }
                }
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        strokeWidth = 2.dp,
                        modifier = Modifier.size(22.dp)
                    )
                } else {
                    Text("Send OTP", color = Color.White)
                }
            }
        }
    }
}
