package com.example.sleepapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Send
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.sleepapp.ui.theme.*

@Composable
fun AiConsultantScreen() {
    val messages = listOf(
        "Good evening. Your resting heart rate was slightly elevated last night. Would you like to try a calming breathing exercise before bed?" to false,
        "Yes, I'm feeling a bit restless. My mind won't stop racing." to true,
        "I understand. Let's try the \"4-7-8\" technique. It's designed to quiet the nervous system." to false
    )
    var text by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(MidnightStart, MidnightEnd)
                )
            )
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { /* TODO: Navigate back */ }) {
                Icon(Icons.Default.ArrowBackIosNew, contentDescription = "Back")
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Luna", style = MaterialTheme.typography.titleLarge)
                Text("Calm Presence", style = MaterialTheme.typography.labelSmall)
            }
            IconButton(onClick = { /* TODO: More options */ }) {
                Icon(Icons.Default.MoreHoriz, contentDescription = "More options")
            }
        }
        LazyColumn(
            modifier = Modifier.weight(1f),
            reverseLayout = true
        ) {
            items(messages.size) { index ->
                ChatMessageBubble(messages[messages.size - 1 - index].first, messages[messages.size - 1 - index].second)
            }
        }

        // Input
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Default.AddCircle, contentDescription = "Add")
            }
            BasicTextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Default.Mic, contentDescription = "Mic")
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Default.Send, contentDescription = "Send")
            }
        }
    }
}

@Composable
fun ChatMessageBubble(message: String, isFromUser: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        horizontalArrangement = if (isFromUser) Arrangement.End else Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .background(
                    if (isFromUser) Primary else CardDark,
                    RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomStart = if (isFromUser) 16.dp else 0.dp,
                        bottomEnd = if (isFromUser) 0.dp else 16.dp
                    )
                )
                .padding(12.dp)
        ) {
            Text(message, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
