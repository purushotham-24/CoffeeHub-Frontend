package com.example.coffeehub.screens.support

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpSupportScreen(nav: NavController) {

    val brown = Color(0xFF5C4033)

    val contacts = listOf(
        ContactOption(Icons.Default.Phone, "Call Us", "+91 98765 43210"),
        ContactOption(Icons.Default.Email, "Email Us", "support@coffeehub.com")
    )

    val faqs = listOf(
        FAQData("How do I place an order?", "Browse menu → Add item → Checkout → Pay via UPI/Card/Wallet"),
        FAQData("Can I cancel my order?", "Orders can be cancelled within 2 minutes after placing."),
        FAQData("How to book workspace?", "Select → Choose Date & Time → Pay → Confirmation."),
        FAQData("Refund policy?", "Refund takes 5–7 business days to process."),
        FAQData("How to track order?", "Check Order Tracking from bottom navigation.")
    )

    var expandedIndex by remember { mutableStateOf(-1) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Help & Support", color = Color.White, fontSize = 20.sp) },
                navigationIcon = {
                    IconButton(onClick = { nav.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = brown)
            )
        }
    ) { pad ->

        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(pad).background(Color.White)
        ) {

            item { Spacer(Modifier.height(14.dp)) }

            // CONTACT SECTION
            item {
                Text("Contact Us", modifier = Modifier.padding(16.dp), fontSize = 18.sp, color = brown)
                contacts.forEach {
                    ContactCard(it)
                    Spacer(Modifier.height(10.dp))
                }
            }

            item { Spacer(Modifier.height(20.dp)) }

            // FAQ SECTION
            item {
                Row(
                    Modifier.padding(horizontal=16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Help, "", tint = brown)
                    Spacer(Modifier.width(6.dp))
                    Text("FAQs", fontSize = 18.sp, color = brown)
                }
            }

            items(faqs) { faq ->
                FAQCard(
                    faq = faq,
                    expanded = faqs.indexOf(faq) == expandedIndex,
                    toggle = {
                        expandedIndex = if (expandedIndex == faqs.indexOf(faq)) -1
                        else faqs.indexOf(faq)
                    }
                )
                Spacer(Modifier.height(10.dp))
            }

            item {
                Spacer(Modifier.height(20.dp))
                Text("Quick Links", modifier = Modifier.padding(16.dp), fontSize = 18.sp, color = brown)
                QuickLink("Terms & Conditions")
                QuickLink("Privacy Policy")
                QuickLink("About Us")
                Spacer(Modifier.height(20.dp))
            }
        }
    }
}

@Composable
fun ContactCard(contact: ContactOption) {
    Row(
        Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(14.dp)).background(Color.White)
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            Modifier.size(46.dp).background(Color(0xFFF5E6CF), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(contact.icon, "", tint = Color(0xFF5C4033))
        }
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            Text(contact.label, fontSize = 16.sp, color = Color(0xFF5C4033))
            Text(contact.value, fontSize = 13.sp, color = Color.Gray)
        }
        Icon(Icons.Default.ChevronRight, "", tint = Color.Gray)
    }
}

@Composable
fun FAQCard(faq: FAQData, expanded:Boolean, toggle:()->Unit) {

    val rotate by animateFloatAsState(if(expanded) 90f else 0f)

    Column(
        Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(Color.White)
            .clickable { toggle() }
            .padding(14.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(faq.question, color = Color(0xFF5C4033), fontSize = 15.sp, modifier = Modifier.weight(1f))
            Icon(Icons.Default.ChevronRight, "", tint = Color.Gray, modifier = Modifier.rotate(rotate))
        }
        if(expanded) {
            Spacer(Modifier.height(6.dp))
            Text(faq.answer, fontSize = 13.sp, color = Color.DarkGray)
        }
    }
}

@Composable fun QuickLink(text:String) =
    Row(
        Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(Color.White)
            .padding(14.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text, color = Color(0xFF5C4033))
        Icon(Icons.Default.ChevronRight, "", tint = Color.Gray)
    }

/* ==== DATA ==== */
data class ContactOption(val icon: ImageVector, val label:String, val value:String)
data class FAQData(val question:String, val answer:String)
