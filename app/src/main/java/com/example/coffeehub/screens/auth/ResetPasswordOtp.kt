@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.coffeehub.screens.auth

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.coffeehub.data.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun ResetPasswordOtp(
    nav: NavController,
    email: String
) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var otp by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMsg by remember { mutableStateOf("") }

    val brown = Color(0xFF5C4033)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Reset Password", color = Color.White) },
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
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {

            Text(
                text = "OTP sent to $email",
                fontSize = 13.sp,
                color = Color.Gray
            )

            OutlinedTextField(
                value = otp,
                onValueChange = { otp = it },
                label = { Text("OTP") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("New Password") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
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

                    if (otp.isBlank() || password.isBlank()) {
                        errorMsg = "All fields required"
                        return@Button
                    }

                    isLoading = true
                    errorMsg = ""

                    scope.launch(Dispatchers.IO) {
                        try {
                            val res =
                                AuthRepository().resetPassword(
                                    email = email,
                                    otp = otp,
                                    password = password
                                )

                            withContext(Dispatchers.Main) {
                                if (res.status) {
                                    Toast.makeText(
                                        context,
                                        "Password updated successfully",
                                        Toast.LENGTH_LONG
                                    ).show()

                                    nav.navigate("login") {
                                        popUpTo(0)
                                    }
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
                    Text("Reset Password", color = Color.White)
                }
            }
        }
    }
}
