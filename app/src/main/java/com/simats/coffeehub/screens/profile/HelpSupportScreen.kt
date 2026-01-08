package com.simats.coffeehub.screens.support

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
        FAQData("How do I place an order?", "Browse → Add → Checkout → Pay"),
        FAQData("Can I cancel my order?", "Cancellation allowed within 2 minutes."),
        FAQData("How to book workspace?", "Select → Date & Time → Pay"),
        FAQData("Refund policy?", "Refund takes 5–7 business days."),
        FAQData("How to track order?", "Use Order Tracking screen.")
    )

    val quickLinks = listOf(
        FAQData(
            "Terms & Conditions",
            """
• Orders cannot be modified once placed
• Cancellation allowed within 2 minutes
• Payments are securely processed
• Misuse may lead to suspension
            """.trimIndent()
        ),
        FAQData(
            "Privacy Policy",
            """
• We do not share user data
• Payments are secure
• Data stored safely
• Location used only for service
            """.trimIndent()
        ),
        FAQData(
            "About CoffeeHub",
            """
CoffeeHub is a smart café app that enables:
• Coffee ordering
• Seat & workspace booking
• Payments & tracking
• AI-based crowd prediction
            """.trimIndent()
        )
    )

    var expandedFaq by remember { mutableStateOf(-1) }
    var expandedQuick by remember { mutableStateOf(-1) }

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
            modifier = Modifier
                .fillMaxSize()
                .padding(pad)
                .background(Color.White)
        ) {

            item { Spacer(Modifier.height(14.dp)) }

            // CONTACT US
            item {
                Text("Contact Us", Modifier.padding(16.dp), fontSize = 18.sp, color = brown)
                contacts.forEach {
                    ContactCard(it)
                    Spacer(Modifier.height(10.dp))
                }
            }

            // FAQ
            item {
                Spacer(Modifier.height(20.dp))
                SectionTitle("FAQs", brown)
            }

            items(faqs) { faq ->
                ExpandableCard(
                    data = faq,
                    expanded = expandedFaq == faqs.indexOf(faq),
                    onClick = {
                        expandedFaq =
                            if (expandedFaq == faqs.indexOf(faq)) -1
                            else faqs.indexOf(faq)
                    }
                )
            }

            // QUICK LINKS
            item {
                Spacer(Modifier.height(20.dp))
                SectionTitle("Quick Links", brown)
            }

            items(quickLinks) { link ->
                ExpandableCard(
                    data = link,
                    expanded = expandedQuick == quickLinks.indexOf(link),
                    onClick = {
                        expandedQuick =
                            if (expandedQuick == quickLinks.indexOf(link)) -1
                            else quickLinks.indexOf(link)
                    }
                )
            }

            item { Spacer(Modifier.height(20.dp)) }
        }
    }
}

/* ---------- COMPONENTS ---------- */

@Composable
fun SectionTitle(text: String, color: Color) {
    Row(
        Modifier.padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Default.Help, "", tint = color)
        Spacer(Modifier.width(6.dp))
        Text(text, fontSize = 18.sp, color = color)
    }
}

@Composable
fun ContactCard(contact: ContactOption) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(Color.White)
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
fun ExpandableCard(
    data: FAQData,
    expanded: Boolean,
    onClick: () -> Unit
) {
    val rotate by animateFloatAsState(if (expanded) 90f else 0f)

    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(Color.White)
            .clickable { onClick() }
            .padding(14.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(data.question, color = Color(0xFF5C4033), fontSize = 15.sp, modifier = Modifier.weight(1f))
            Icon(Icons.Default.ChevronRight, "", tint = Color.Gray, modifier = Modifier.rotate(rotate))
        }
        if (expanded) {
            Spacer(Modifier.height(6.dp))
            Text(data.answer, fontSize = 13.sp, color = Color.DarkGray)
        }
    }
}

/* ---------- DATA ---------- */

data class ContactOption(val icon: ImageVector, val label: String, val value: String)
data class FAQData(val question: String, val answer: String)
