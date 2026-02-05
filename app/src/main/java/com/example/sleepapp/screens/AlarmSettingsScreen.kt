package com.example.sleepapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.sleepapp.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun AlarmSettingsScreen() {
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
            Text("Edit Alarm", style = MaterialTheme.typography.titleLarge)
            TextButton(onClick = { /* TODO: Save */ }) {
                Text("Save", color = Primary, fontWeight = FontWeight.Bold)
            }
        }
        TimeWheelPicker()
        Spacer(modifier = Modifier.height(24.dp))
        SmartWakeUpSetting()
        Spacer(modifier = Modifier.height(24.dp))
        ScheduleSettings()
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = { /* TODO */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(56.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("Enable Alarm")
        }
    }
}

@Composable
fun SmartWakeUpSetting() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardDark)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text("Smart Wake-up", style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold))
                Text(
                    "Wake up gently within 30 min window",
                    style = MaterialTheme.typography.bodySmall.copy(color = Color.White.copy(alpha = 0.7f))
                )
            }
            Switch(checked = true, onCheckedChange = {})
        }
    }
}

@Composable
fun ScheduleSettings() {
    val days = listOf("M", "T", "W", "T", "F", "S", "S")
    var selectedDays by remember { mutableStateOf(setOf("M", "T", "W", "T", "F")) }

    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Text("Schedule", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            days.forEach { day ->
                val isSelected = selectedDays.contains(day)
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(if (isSelected) Primary else CardDark)
                        .clickable {
                            selectedDays = if (isSelected) {
                                selectedDays - day
                            } else {
                                selectedDays + day
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(day, color = if (isSelected) Color.White else Color.White.copy(alpha = 0.7f))
                }
            }
        }
    }
}

@Composable
fun TimeWheelPicker() {
    val hours = (0..23).map { "%02d".format(it) }
    val minutes = (0..59).map { "%02d".format(it) }

    val hourState = rememberLazyListState(initialFirstVisibleItemIndex = 7)
    val minuteState = rememberLazyListState(initialFirstVisibleItemIndex = 30)

    val coroutineScope = rememberCoroutineScope()

    var selectedHour by remember { mutableStateOf(hours[7]) }
    var selectedMinute by remember { mutableStateOf(minutes[30]) }

    LaunchedEffect(hourState.isScrollInProgress) {
        if (!hourState.isScrollInProgress) {
            val centerIndex = hourState.firstVisibleItemIndex
            selectedHour = hours[centerIndex]
            hourState.animateScrollToItem(centerIndex)
        }
    }

    LaunchedEffect(minuteState.isScrollInProgress) {
        if (!minuteState.isScrollInProgress) {
            val centerIndex = minuteState.firstVisibleItemIndex
            selectedMinute = minutes[centerIndex]
            minuteState.animateScrollToItem(centerIndex)
        }
    }


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Hours
            LazyColumn(
                state = hourState,
                modifier = Modifier.width(100.dp),
                horizontalAlignment = Alignment.End
            ) {
                items(hours.size) { hour ->
                    Text(
                        text = hours[hour],
                        style = MaterialTheme.typography.displayLarge,
                        modifier = Modifier.padding(vertical = 4.dp),
                        color = if (hours[hour] == selectedHour) AccentAmber else Color.White.copy(alpha = 0.5f)
                    )
                }
            }

            Text(":", style = MaterialTheme.typography.displayLarge, color = AccentAmber, modifier = Modifier.padding(horizontal = 8.dp))

            // Minutes
            LazyColumn(
                state = minuteState,
                modifier = Modifier.width(100.dp),
                horizontalAlignment = Alignment.Start
            ) {
                items(minutes.size) { minute ->
                    Text(
                        text = minutes[minute],
                        style = MaterialTheme.typography.displayLarge,
                        modifier = Modifier.padding(vertical = 4.dp),
                        color = if (minutes[minute] == selectedMinute) AccentAmber else Color.White.copy(alpha = 0.5f)
                    )
                }
            }
        }
    }
}
