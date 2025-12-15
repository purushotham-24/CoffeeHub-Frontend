@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.coffeehub.screens.auth

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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

@Composable
fun ForgotPassword(nav: NavController) {

    var email by remember { mutableStateOf("") }
    val context = LocalContext.current

    val brown = Color(0xFF5C4033)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Forgot Password", fontSize = 20.sp, color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { nav.popBackStack() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = brown)
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Enter your email to reset your password.",
                fontSize = 16.sp,
                color = brown
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = brown,
                    unfocusedBorderColor = brown.copy(alpha = 0.4f),
                    cursorColor = brown
                )
            )

            Button(
                onClick = {
                    Toast.makeText(context, "Reset link sent!", Toast.LENGTH_SHORT).show()
                    nav.popBackStack()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                colors = ButtonDefaults.buttonColors(containerColor = brown)
            ) {
                Text("Send Reset Link", fontSize = 18.sp, color = Color.White)
            }
        }
    }
}
