@file:OptIn(ExperimentalMaterial3Api::class)

package com.simats.coffeehub.screens.profile

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.simats.coffeehub.data.repository.ProfileRepository
import kotlinx.coroutines.launch

@Composable
fun EditProfileScreen(nav: NavController) {

    val context = nav.context
    val prefs = context.getSharedPreferences("coffeehub_prefs", Context.MODE_PRIVATE)
    val repo = remember { ProfileRepository() }
    val scope = rememberCoroutineScope()

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }

    var isSaving by remember { mutableStateOf(false) }

    val brown = Color(0xFF5C4033)
    val cream = Color(0xFFF5E6CF)

    /* ---------- LOAD PROFILE FROM DB ---------- */
    LaunchedEffect(Unit) {
        val userId = prefs.getInt("user_id", 0)

        if (userId != 0) {
            try {
                val res = repo.getProfile(userId)
                if (res.status && res.data != null) {
                    name = res.data.name ?: ""
                    email = res.data.email ?: ""
                    phone = res.data.phone ?: ""
                    dob = res.data.dob ?: ""
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Failed to load profile", Toast.LENGTH_SHORT).show()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Profile", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { nav.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, null, tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = brown)
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            /* ---------- AVATAR ---------- */
            Box(contentAlignment = Alignment.BottomEnd) {
                Box(
                    modifier = Modifier
                        .size(110.dp)
                        .clip(CircleShape)
                        .background(brown),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = name.takeIf { it.isNotBlank() }
                            ?.take(2)
                            ?.uppercase() ?: "U",
                        color = Color.White,
                        fontSize = 34.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(cream)
                ) {
                    Icon(Icons.Default.CameraAlt, null, tint = brown)
                }
            }

            /* ---------- FIELDS ---------- */
            EditField("Full Name", name) { name = it }
            EditField("Email", email) { email = it }
            EditField("Phone Number", phone) { phone = it }
            EditField("Date of Birth (YYYY-MM-DD)", dob) { dob = it }

            /* ---------- SAVE ---------- */
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                enabled = !isSaving,
                colors = ButtonDefaults.buttonColors(containerColor = brown),
                onClick = {
                    val userId = prefs.getInt("user_id", 0)
                    if (userId == 0) {
                        Toast.makeText(context, "User not found", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    isSaving = true
                    scope.launch {
                        try {
                            val res = repo.updateProfile(
                                userId,
                                name,
                                email,
                                phone,
                                dob
                            )

                            if (res.status) {
                                Toast.makeText(
                                    context,
                                    "Profile updated",
                                    Toast.LENGTH_SHORT
                                ).show()
                                nav.popBackStack()
                            } else {
                                Toast.makeText(
                                    context,
                                    "Update failed",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } catch (e: Exception) {
                            Toast.makeText(
                                context,
                                "Network error",
                                Toast.LENGTH_SHORT
                            ).show()
                        } finally {
                            isSaving = false
                        }
                    }
                }
            ) {
                if (isSaving) {
                    CircularProgressIndicator(
                        color = Color.White,
                        strokeWidth = 2.dp,
                        modifier = Modifier.size(22.dp)
                    )
                } else {
                    Icon(Icons.Default.Save, null, tint = Color.White)
                    Spacer(Modifier.width(8.dp))
                    Text("Save Changes", color = Color.White)
                }
            }
        }
    }
}

/* ---------- INPUT FIELD ---------- */
@Composable
fun EditField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    val brown = Color(0xFF5C4033)

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        shape = RoundedCornerShape(18.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = brown,
            unfocusedBorderColor = brown.copy(alpha = 0.4f),
            cursorColor = brown
        )
    )
}
