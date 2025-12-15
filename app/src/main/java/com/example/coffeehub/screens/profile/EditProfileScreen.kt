@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.coffeehub.screens.profile

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.*
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

@Composable
fun EditProfileScreen(nav: NavController) {

    val prefs = nav.context.getSharedPreferences("coffeehub_prefs", Context.MODE_PRIVATE)
    val userId = prefs.getInt("user_id", -1)

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }

    var isLoading by remember { mutableStateOf(false) }

    val brown = Color(0xFF5C4033)
    val cream = Color(0xFFF5E6CF)

    // ===== LOAD EXISTING PROFILE =====
    LaunchedEffect(Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val apiUrl = "http://192.168.235.241/CoffeeHub/get_profile.php"

                val json = JSONObject().apply {
                    put("user_id", userId)
                }

                val url = URL(apiUrl)
                val conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "POST"
                conn.setRequestProperty("Content-Type", "application/json")
                conn.doOutput = true

                conn.outputStream.use { it.write(json.toString().toByteArray()) }

                val response = conn.inputStream.bufferedReader().readText()
                val res = JSONObject(response)

                if (res.getBoolean("success")) {
                    name = res.getString("name")
                    email = res.getString("email")
                    phone = res.optString("phone", "")
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(nav.context, "Failed to load profile", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // ===== UI =====
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Profile", fontSize = 20.sp, color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { nav.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null, tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = brown)
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Avatar
            Box(contentAlignment = Alignment.BottomEnd) {
                Box(
                    modifier = Modifier
                        .size(110.dp)
                        .clip(CircleShape)
                        .background(brown),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = name.take(2).uppercase(),
                        color = Color.White,
                        fontSize = 34.sp,
                        textAlign = TextAlign.Center
                    )
                }

                IconButton(
                    onClick = { /* image picker future */ },
                    modifier = Modifier
                        .offset(x = (-4).dp, y = (-4).dp)
                        .size(35.dp)
                        .clip(CircleShape)
                        .background(cream)
                ) {
                    Icon(Icons.Default.CameraAlt, contentDescription = null, tint = brown)
                }
            }

            // Input Fields
            EditField("Full Name", name) { name = it }
            EditField("Email", email) { email = it }
            EditField("Phone Number", phone) { phone = it }
            EditField("Date of Birth", dob) { dob = it }

            Spacer(Modifier.height(20.dp))

            // SAVE BUTTON
            Button(
                onClick = {
                    isLoading = true

                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            val apiUrl = "http://192.168.235.241/CoffeeHub/update_profile.php"

                            val json = JSONObject().apply {
                                put("user_id", userId)
                                put("name", name)
                                put("email", email)
                                put("phone", phone)
                            }

                            val url = URL(apiUrl)
                            val conn = url.openConnection() as HttpURLConnection
                            conn.requestMethod = "POST"
                            conn.setRequestProperty("Content-Type", "application/json")
                            conn.doOutput = true

                            conn.outputStream.use { it.write(json.toString().toByteArray()) }

                            val response = conn.inputStream.bufferedReader().readText()
                            val res = JSONObject(response)

                            withContext(Dispatchers.Main) {
                                isLoading = false
                                Toast.makeText(nav.context, res.getString("message"), Toast.LENGTH_SHORT).show()
                                if (res.getBoolean("success")) {
                                    nav.popBackStack()
                                }
                            }

                        } catch (e: Exception) {
                            withContext(Dispatchers.Main) {
                                isLoading = false
                                Toast.makeText(nav.context, "Update failed", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                colors = ButtonDefaults.buttonColors(containerColor = brown)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(color = Color.White, strokeWidth = 2.dp)
                } else {
                    Icon(Icons.Default.Save, contentDescription = null, tint = Color.White)
                    Spacer(Modifier.width(10.dp))
                    Text("Save Changes", color = Color.White, fontSize = 18.sp)
                }
            }
        }
    }
}

@Composable
fun EditField(label: String, value: String, onValueChange: (String) -> Unit) {
    val brown = Color(0xFF5C4033)

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = brown,
            unfocusedBorderColor = brown.copy(alpha = 0.4f),
            cursorColor = brown
        )
    )
}
