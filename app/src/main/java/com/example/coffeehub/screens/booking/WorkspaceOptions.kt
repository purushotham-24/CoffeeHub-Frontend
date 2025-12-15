package com.example.coffeehub.screens.booking

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(cream)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        Text(
            "Choose Workspace",
            fontSize = 26.sp,
            fontWeight = FontWeight.ExtraBold,
            color = brown
        )

        Text(
            "Select a space that suits your work needs 💼",
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(Modifier.height(6.dp))

        val workspaces = listOf(
            WorkspaceModel("1","Solo Workspace",199,"1 Person"),
            WorkspaceModel("2","Duo Workspace",399,"2 People"),
            WorkspaceModel("3","Team Workspace",599,"4–6 People")
        )

        workspaces.forEach { item ->
            WorkspaceCard(item) { nav.navigate("workspace_details/${item.id}") }
        }
    }
}

@Composable
fun WorkspaceCard(m: WorkspaceModel, onClick:()->Unit) {

    val brown = Color(0xFF5C4033)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .shadow(
                elevation = 6.dp,
                shape = RoundedCornerShape(20.dp),
                ambientColor = Color.Black.copy(alpha = 0.1f),
                spotColor = Color.Black.copy(alpha = 0.1f)
            ),
        colors = CardDefaults.cardColors(Color.White),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(18.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            // Title Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(m.name, fontSize = 21.sp, fontWeight = FontWeight.Bold, color = brown)

                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .background(brown, CircleShape)
                )  // small premium indicator dot
            }

            Text(m.capacity, fontSize = 13.sp, color = Color.Gray)

            // Price Tag
            Box(
                modifier = Modifier
                    .background(
                        Brush.horizontalGradient(listOf(brown, brown.copy(.85f))),
                        RoundedCornerShape(14.dp)
                    )
                    .padding(vertical = 6.dp, horizontal = 14.dp)
            ) {
                Text("₹${m.price}/hr", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
        }
    }
}

data class WorkspaceModel(
    val id:String,
    val name:String,
    val price:Int,
    val capacity:String
)
