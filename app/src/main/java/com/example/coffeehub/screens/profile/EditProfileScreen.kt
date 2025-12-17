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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun EditProfileScreen(nav: NavController) {

    /* ---------------- CONTEXT ---------------- */
    val context = nav.context
    val prefs = context.getSharedPreferences("coffeehub_prefs", Context.MODE_PRIVATE)

    /* ---------------- STATE ---------------- */
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }

    var isSaving by remember { mutableStateOf(false) }

    /* ---------------- COLORS ---------------- */
    val brown = Color(0xFF5C4033)
    val cream = Color(0xFFF5E6CF)

    /* ---------------- LOAD LOCAL PROFILE ---------------- */
    LaunchedEffect(Unit) {
        name = prefs.getString("profile_name", "") ?: ""
        email = prefs.getString("profile_email", "") ?: ""
        phone = prefs.getString("profile_phone", "") ?: ""
        dob = prefs.getString("profile_dob", "") ?: ""
    }

    /* ---------------- UI ---------------- */
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Edit Profile",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { nav.popBackStack() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
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
            verticalArrangement = Arrangement.spacedBy(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            /* ---------------- AVATAR ---------------- */
            Box(contentAlignment = Alignment.BottomEnd) {
                Box(
                    modifier = Modifier
                        .size(110.dp)
                        .clip(CircleShape)
                        .background(brown),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = name
                            .trim()
                            .takeIf { it.isNotEmpty() }
                            ?.take(2)
                            ?.uppercase()
                            ?: "U",
                        color = Color.White,
                        fontSize = 34.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }

                IconButton(
                    onClick = { /* image picker later */ },
                    modifier = Modifier
                        .offset(x = (-4).dp, y = (-4).dp)
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(cream)
                ) {
                    Icon(
                        Icons.Default.CameraAlt,
                        contentDescription = "Change photo",
                        tint = brown
                    )
                }
            }

            Spacer(Modifier.height(6.dp))

            /* ---------------- INPUT FIELDS ---------------- */
            EditField("Full Name", name) { name = it }
            EditField("Email", email) { email = it }
            EditField("Phone Number", phone) { phone = it }
            EditField("Date of Birth", dob) { dob = it }

            Spacer(Modifier.height(14.dp))

            /* ---------------- SAVE BUTTON ---------------- */
            Button(
                onClick = {
                    isSaving = true

                    prefs.edit()
                        .putString("profile_name", name)
                        .putString("profile_email", email)
                        .putString("profile_phone", phone)
                        .putString("profile_dob", dob)
                        .apply()

                    Toast.makeText(context, "Profile updated", Toast.LENGTH_SHORT).show()
                    isSaving = false
                    nav.popBackStack()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                enabled = !isSaving,
                colors = ButtonDefaults.buttonColors(containerColor = brown)
            ) {
                if (isSaving) {
                    CircularProgressIndicator(
                        color = Color.White,
                        strokeWidth = 2.dp,
                        modifier = Modifier.size(22.dp)
                    )
                } else {
                    Icon(Icons.Default.Save, contentDescription = null, tint = Color.White)
                    Spacer(Modifier.width(10.dp))
                    Text(
                        "Save Changes",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

/* ---------------- INPUT FIELD ---------------- */
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
        shape = RoundedCornerShape(18.dp),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = brown,
            unfocusedBorderColor = brown.copy(alpha = 0.4f),
            cursorColor = brown
        )
    )
}
