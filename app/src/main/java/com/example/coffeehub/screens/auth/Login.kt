package com.example.coffeehub.screens.auth

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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.coffeehub.R
import com.example.coffeehub.data.network.RetrofitClient
import com.example.coffeehub.data.repository.AuthRepository
import com.example.coffeehub.utils.GoogleSignInHelper
import com.example.coffeehub.utils.SessionManager
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

        if (result.resultCode != Activity.RESULT_OK) {
            return@rememberLauncherForActivityResult // silent cancel
        }

        try {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            val account = task.result
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

                Spacer(Modifier.height(20.dp))

                Button(
                    onClick = { /* sign in */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = brown)
                ) {
                    Text("Sign In", fontSize = 18.sp, color = Color.White)
                }

                Spacer(Modifier.height(14.dp))

                /* ===== GOOGLE SIGN-IN (ICON SIZE FIXED) ===== */
                OutlinedButton(
                    onClick = {
                        googleHelper.client.signOut().addOnCompleteListener {
                            googleLauncher.launch(
                                googleHelper.client.signInIntent
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    shape = RoundedCornerShape(14.dp)
                ) {

                    // ✅ Bigger Google icon with white background
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .background(Color.White, shape = RoundedCornerShape(50)),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.google_logo),
                            contentDescription = "Google",
                            modifier = Modifier.size(22.dp)
                        )
                    }

                    Spacer(Modifier.width(12.dp))
                    Text("Sign in with Google")
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
