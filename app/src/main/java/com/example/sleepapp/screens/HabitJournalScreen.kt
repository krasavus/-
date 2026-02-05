package com.example.sleepapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.filled.Close
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.sleepapp.ui.theme.*

@Composable
fun HabitJournalScreen() {
    val habits = listOf(
        "Caffeine" to Icons.Default.Coffee,
        "Alcohol" to Icons.Default.WineBar,
        "Heavy Meal" to Icons.Default.Restaurant,
        "Exercise" to Icons.Default.FitnessCenter,
        "Screen Time" to Icons.Default.Smartphone,
        "Meditation" to Icons.Default.SelfImprovement
    )
    var selectedHabits by remember { mutableStateOf(setOf("Caffeine")) }
    var stressLevel by remember { mutableFloatStateOf(3f) }
    var notes by remember { mutableStateOf("") }


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
            Text("Good Evening", style = MaterialTheme.typography.titleLarge)
            IconButton(onClick = { /* TODO: Navigate back */ }) {
                Icon(Icons.Default.Close, contentDescription = "Close")
            }
        }
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    "How was your day?",
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    "Select factors that might affect your sleep tonight.",
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.White.copy(alpha = 0.7f))
                )
            }
            item {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.height(300.dp) // Adjust height as needed
                ) {
                    items(habits.size) { index ->
                        val habit = habits[index]
                        HabitCard(
                            text = habit.first,
                            icon = habit.second,
                            isSelected = selectedHabits.contains(habit.first),
                            onSelect = {
                                selectedHabits = if (selectedHabits.contains(habit.first)) {
                                    selectedHabits - habit.first
                                } else {
                                    selectedHabits + habit.first
                                }
                            }
                        )
                    }
                }
            }
            item { StressSlider(stressLevel) { stressLevel = it } }
            item {
                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Evening Thoughts") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = CardDark,
                        unfocusedContainerColor = CardDark,
                        disabledContainerColor = CardDark,
                    )
                )
            }
            item {
                Button(
                    onClick = { /* TODO: Save */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("Save & Sleep")
                }
            }
        }
    }
}

@Composable
fun StressSlider(stressLevel: Float, onStressLevelChange: (Float) -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardDark)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Stress Level", style = MaterialTheme.typography.bodyLarge)
                Text(
                    when (stressLevel) {
                        in 1f..2f -> "Low"
                        in 2f..4f -> "Medium"
                        else -> "High"
                    }, style = MaterialTheme.typography.bodyLarge.copy(color = Primary)
                )
            }
            Slider(
                value = stressLevel,
                onValueChange = onStressLevelChange,
                valueRange = 1f..5f,
                steps = 3
            )
        }
    }
}

@Composable
fun HabitCard(
    text: String,
    icon: ImageVector,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Card(
        modifier = Modifier
            .aspectRatio(1f)
            .border(
                width = 2.dp,
                color = if (isSelected) Primary else Color.Transparent,
                shape = RoundedCornerShape(16.dp)
            )
            .clip(RoundedCornerShape(16.dp))
            .clickable { onSelect() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Primary.copy(alpha = 0.1f) else CardDark
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start
        ) {
            Icon(
                imageVector = icon,
                contentDescription = text,
                tint = if (isSelected) Primary else Color.White,
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        if (isSelected) Primary.copy(alpha = 0.2f) else Color.White.copy(alpha = 0.1f),
                        CircleShape
                    )
                    .padding(8.dp)
            )
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                color = if (isSelected) Primary else Color.White
            )
        }
    }
}
