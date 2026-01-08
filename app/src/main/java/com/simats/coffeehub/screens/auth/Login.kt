package com.simats.coffeehub.screens.auth

import android.app.Activity
import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.simats.coffeehub.R
import com.simats.coffeehub.data.network.RetrofitClient
import com.simats.coffeehub.utils.GoogleSignInHelper
import com.simats.coffeehub.utils.SessionManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import kotlinx.coroutines.launch

@Composable
fun Login(nav: NavController) {

    val context = LocalContext.current
    val activity = context as Activity
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

    /* ================= GOOGLE SIGN-IN ================= */

    val googleHelper = remember { GoogleSignInHelper(activity) }

    val googleLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->

        if (result.resultCode != Activity.RESULT_OK) return@rememberLauncherForActivityResult

        try {
            val account =
                GoogleSignIn.getSignedInAccountFromIntent(result.data).result
            val idToken = account.idToken ?: return@rememberLauncherForActivityResult

            googleHelper.firebaseLogin(
                idToken,
                onSuccess = { gEmail, gName, gId ->
                    scope.launch {
                        try {
                            val res = RetrofitClient.api.googleLogin(
                                mapOf(
                                    "name" to (gName ?: ""),
                                    "email" to gEmail,
                                    "google_id" to gId
                                )
                            )

                            val status = res["status"] as? Boolean ?: false
                            val userId = (res["userId"] as? Number)?.toInt() ?: -1

                            if (status && userId > 0) {
                                SessionManager.userId = userId
                                prefs.edit().putInt("user_id", userId).apply()

                                if (rememberMe) {
                                    prefs.edit()
                                        .putString("email", gEmail)
                                        .putBoolean("remember", true)
                                        .apply()
                                }

                                nav.navigate("home") {
                                    popUpTo("login") { inclusive = true }
                                }
                            } else {
                                errorMsg = "Google login failed"
                            }
                        } catch (e: Exception) {
                            errorMsg = "Server error"
                        }
                    }
                },
                onError = { errorMsg = it }
            )

        } catch (e: Exception) {
            errorMsg = "Google sign-in failed"
        }
    }

    /* ================= EMAIL LOGIN ================= */

    fun emailLogin() {

        if (email.isBlank() || password.isBlank()) {
            errorMsg = "Email and password required"
            return
        }

        // ADMIN LOGIN
        if (email == "coffeehub376@gmail.com" && password == "Welcome@24") {
            SessionManager.userId = 0
            prefs.edit().putInt("user_id", 0).apply()

            nav.navigate("admin_home") {
                popUpTo("login") { inclusive = true }
            }
            return
        }

        isLoading = true
        errorMsg = ""

        scope.launch {
            try {
                val res = RetrofitClient.api.login(
                    mapOf(
                        "email" to email.trim(),
                        "password" to password
                    )
                )

                if (res.status && res.data != null) {

                    val userId = (res.data["user_id"] as Number).toInt()
                    SessionManager.userId = userId
                    prefs.edit().putInt("user_id", userId).apply()

                    if (rememberMe) {
                        prefs.edit()
                            .putString("email", email)
                            .putString("password", password)
                            .putBoolean("remember", true)
                            .apply()
                    } else {
                        prefs.edit().clear().apply()
                    }

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

    /* ================= UI ================= */

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

                Text("Welcome Back ☕", fontSize = 26.sp, fontWeight = FontWeight.SemiBold, color = brown)
                Text("Sign in to continue", fontSize = 14.sp, color = Color.Gray)

                Spacer(Modifier.height(24.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    leadingIcon = { Icon(Icons.Default.Email, null) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
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
                                contentDescription = "Toggle password"
                            )
                        }
                    },
                    visualTransformation =
                        if (passwordVisible) VisualTransformation.None
                        else PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(Modifier.height(6.dp))

                Text(
                    text = "Forgot password?",
                    color = brown,
                    fontSize = 13.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { nav.navigate("forgot-password") },
                    textAlign = androidx.compose.ui.text.style.TextAlign.End
                )

                Spacer(Modifier.height(6.dp))

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

                Spacer(Modifier.height(18.dp))

                Button(
                    onClick = { emailLogin() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    enabled = !isLoading,
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = brown)
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(22.dp),
                            color = Color.White,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text("Sign In", fontSize = 18.sp, color = Color.White)
                    }
                }

                Spacer(Modifier.height(12.dp))

                /* ===== GOOGLE SIGN IN BUTTON ===== */
                OutlinedButton(
                    onClick = {
                        googleHelper.client.signOut().addOnCompleteListener {
                            googleLauncher.launch(googleHelper.client.signInIntent)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    shape = RoundedCornerShape(14.dp)
                ) {

                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .background(Color.White, RoundedCornerShape(50)),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.google_logo),
                            contentDescription = "Google",
                            modifier = Modifier.size(22.dp)
                        )
                    }

                    Spacer(Modifier.width(12.dp))
                    Text("Sign in with Google", fontWeight = FontWeight.Medium)
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

        /* ===== FOOTER ===== */
        Text(
            text = "2026 © Powered by SIMATS Engineering",
            fontSize = 12.sp,
            color = Color.White.copy(alpha = 0.8f),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
        )
    }
}
