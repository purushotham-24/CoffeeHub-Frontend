package com.example.coffeehub.screens.booking

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun WorkspaceOptions(nav: NavController) {

    val brown = Color(0xFF5C4033)
    val cream = Color(0xFFF7EFE6)

    val workspaces = listOf(
        WorkspaceModel("1", "Solo Workspace", 199, "1 Person"),
        WorkspaceModel("2", "Duo Workspace", 399, "2 People"),
        WorkspaceModel("3", "Team Workspace", 599, "4–6 People")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(cream)
    ) {

        // 🔝 TOP BAR WITH BACK BUTTON
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(brown)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier
                    .size(26.dp)
                    .clickable { nav.popBackStack() }
            )

            Spacer(Modifier.width(12.dp))

            Text(
                text = "Choose Workspace",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        // 🔽 CONTENT
        Column(
            modifier = Modifier
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            Text(
                text = "Select a space that suits your work needs 💼",
                fontSize = 14.sp,
                color = Color.Gray
            )

            workspaces.forEach { item ->
                WorkspaceCard(item) {

                    // ✅ SAVE WORKSPACE DETAILS (IMPORTANT)
                    BookingManager.bookingType.value = "workspace"
                    BookingManager.workspaceId.value = item.id
                    BookingManager.workspaceName.value = item.name

                    nav.navigate("workspace_details/${item.id}")
                }
            }
        }
    }
}

@Composable
fun WorkspaceCard(
    m: WorkspaceModel,
    onClick: () -> Unit
) {
    val brown = Color(0xFF5C4033)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .shadow(6.dp, RoundedCornerShape(20.dp)),
        colors = CardDefaults.cardColors(Color.White),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = m.name,
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Bold,
                    color = brown
                )

                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .background(brown, CircleShape)
                )
            }

            Text(
                text = m.capacity,
                fontSize = 13.sp,
                color = Color.Gray
            )

            Box(
                modifier = Modifier
                    .background(
                        Brush.horizontalGradient(
                            listOf(brown, brown.copy(alpha = 0.85f))
                        ),
                        RoundedCornerShape(14.dp)
                    )
                    .padding(vertical = 6.dp, horizontal = 14.dp)
            ) {
                Text(
                    text = "₹${m.price}/hr",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

// 📦 MODEL
data class WorkspaceModel(
    val id: String,
    val name: String,
    val price: Int,
    val capacity: String
)
