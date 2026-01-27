package com.example.sleepapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sleepapp.ui.theme.LightBlue
import com.example.sleepapp.ui.theme.TextGray

@Composable
fun StatsScreen() {
    var selectedPeriod by remember { mutableStateOf("Weekly") }
    val sleepData = mapOf(
        "Weekly" to listOf(6.5f, 7f, 8f, 5.5f, 7.5f, 6f, 8.5f),
        "Monthly" to List(30) { (5..9).random().toFloat() } // Dummy data for monthly
    )
    val days = if (selectedPeriod == "Weekly") listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun") else List(30) { (it + 1).toString() }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Sleep Statistics",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        TimePeriodSelector(selectedPeriod) { period ->
            selectedPeriod = period
        }

        Spacer(modifier = Modifier.height(24.dp))

        SleepBarChart(data = sleepData[selectedPeriod]!!, labels = days)

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            StatCard(title = "Average Sleep", value = "7h 15m", modifier = Modifier.weight(1f))
            StatCard(title = "Sleep Quality", value = "85%", modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun TimePeriodSelector(selectedPeriod: String, onPeriodSelected: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(12.dp))
            .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        val periods = listOf("Weekly", "Monthly")
        periods.forEach { period ->
            val isSelected = period == selectedPeriod
            TextButton(
                onClick = { onPeriodSelected(period) },
                modifier = Modifier
                    .weight(1f)
                    .background(
                        if (isSelected) LightBlue else MaterialTheme.colorScheme.surface,
                        RoundedCornerShape(8.dp)
                    )
            ) {
                Text(
                    text = period,
                    color = if (isSelected) MaterialTheme.colorScheme.onPrimary else TextGray
                )
            }
        }
    }
}

@Composable
fun SleepBarChart(data: List<Float>, labels: List<String>) {
    val maxSleep = data.maxOrNull() ?: 1f
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom
        ) {
            data.forEachIndexed { index, sleepHours ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom,
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .width(20.dp)
                            .fillMaxHeight(sleepHours / maxSleep)
                            .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                            .background(LightBlue)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    if (labels.size == 7) { // Only show labels for weekly view to avoid clutter
                        Text(
                            text = labels[index],
                            fontSize = 12.sp,
                            color = TextGray
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun StatCard(title: String, value: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium.copy(color = TextGray)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center
            )
        }
    }
}
