package com.example.coffeehub.screens.auth

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.coffeehub.data.repository.AuthRepository
import com.example.coffeehub.utils.SessionManager
import kotlinx.coroutines.launch

@Composable
fun Login(nav: NavController) {

    val context = LocalContext.current
    val prefs = context.getSharedPreferences("coffeehub_prefs", Context.MODE_PRIVATE)
    val scope = rememberCoroutineScope()

    val brown = Color(0xFF5C4033)
    val cream = Color(0xFFF5E6CF)

    var email by remember { mutableStateOf(prefs.getString("email", "") ?: "") }
    var password by remember { mutableStateOf(prefs.getString("password", "") ?: "") }
    var rememberMe by remember { mutableStateOf(prefs.getBoolean("remember", false)) }

    var errorMsg by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }

    fun saveCredentials() {
        prefs.edit()
            .putString("email", if (rememberMe) email else "")
            .putString("password", if (rememberMe) password else "")
            .putBoolean("remember", rememberMe)
            .apply()
    }

    fun signIn() {
        errorMsg = ""
        val cleanEmail = email.trim()

        if (cleanEmail.isBlank()) {
            errorMsg = "Enter Email"
            return
        }

        if (password.length < 4) {
            errorMsg = "Password must be 4+ characters"
            return
        }

        /* ================= ADMIN LOGIN (FIXED) ================= */
        if (cleanEmail == "coffeehub376@gmail.com" && password == "Welcome@24") {

            // Save admin session
            SessionManager.userId = 0
            prefs.edit().putInt("user_id", 0).apply()
            saveCredentials()

            nav.navigate("admin_home") {
                popUpTo("login") { inclusive = true }
            }
            return
        }
        /* ======================================================= */

        isLoading = true

        scope.launch {
            try {
                val res = AuthRepository().login(cleanEmail, password)

                if (res.status && res.data != null) {

                    val userId = (res.data["user_id"] as Number).toInt()

                    SessionManager.userId = userId
                    prefs.edit().putInt("user_id", userId).apply()
                    saveCredentials()

                    nav.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }

                } else {
                    errorMsg = res.message
                }

            } catch (e: Exception) {
                errorMsg = "Network error. Try again."
            } finally {
                isLoading = false
            }
        }
    }

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

                Text("Welcome Back ☕", fontSize = 28.sp, color = brown)
                Text("Sign in to continue", fontSize = 14.sp, color = Color.Gray)

                Spacer(Modifier.height(25.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    leadingIcon = { Icon(Icons.Default.Email, null) },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(14.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    leadingIcon = { Icon(Icons.Default.Lock, null) },
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                if (passwordVisible)
                                    Icons.Filled.VisibilityOff
                                else
                                    Icons.Filled.Visibility,
                                null
                            )
                        }
                    },
                    visualTransformation =
                        if (passwordVisible) VisualTransformation.None
                        else PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = rememberMe,
                        onCheckedChange = { rememberMe = it }
                    )
                    Text("Remember Me", fontSize = 14.sp, color = Color.Gray)
                }

                if (errorMsg.isNotEmpty()) {
                    Spacer(Modifier.height(8.dp))
                    Text(errorMsg, color = Color.Red, fontSize = 13.sp)
                }

                Spacer(Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = "Forgot Password?",
                        color = brown,
                        fontSize = 14.sp,
                        modifier = Modifier.clickable {
                            nav.navigate("forgot-password")
                        }
                    )
                }

                Spacer(Modifier.height(22.dp))

                Button(
                    onClick = { signIn() },
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
                        Spacer(Modifier.width(12.dp))
                        Text("Signing in...", color = Color.White)
                    } else {
                        Text("Sign In", fontSize = 18.sp, color = Color.White)
                    }
                }

                Spacer(Modifier.height(14.dp))

                Row {
                    Text("Don't have an account? ", color = Color.Gray)
                    Text(
                        "Sign Up",
                        color = brown,
                        modifier = Modifier.clickable {
                            nav.navigate("register")
                        }
                    )
                }
            }
        }
    }
}
