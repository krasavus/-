package com.example.sleepapp.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Share
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.sleepapp.ui.theme.*

@Composable
fun StatsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(MidnightStart, MidnightEnd)
                )
            )
    ) {
        StatsHeader()
        LazyColumn {
            item { DateChips() }
            item { SleepScoreIndicator() }
            item { Spacer(modifier = Modifier.height(24.dp)) }
            item { InsightCards() }
            item { Spacer(modifier = Modifier.height(24.dp)) }
            item { SleepCyclesChart() }
            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}

@Composable
fun SleepCyclesChart() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(250.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardDark)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Chart Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Sleep Cycles", style = MaterialTheme.typography.titleMedium)
                // Legend
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    LegendItem("Light", Color(0xFF06b6d4))
                    LegendItem("REM", Color(0xFF8b5cf6))
                    LegendItem("Deep", Primary)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            // Chart Bars
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                val days = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
                val data by remember {
                    mutableStateOf((1..7).map {
                        val light = (20..40).random()
                        val rem = (20..30).random()
                        val deep = (15..25).random()
                        listOf(light, rem, deep)
                    })
                }

                days.forEachIndexed { index, day ->
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        Row(
                            modifier = Modifier
                                .weight(1f)
                                .width(20.dp),
                            verticalAlignment = Alignment.Bottom
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight(data[index][0] / 100f)
                                    .width(20.dp)
                                    .background(Color(0xFF06b6d4), RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight(data[index][1] / 100f)
                                    .width(20.dp)
                                    .background(Color(0xFF8b5cf6))
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight(data[index][2] / 100f)
                                    .width(20.dp)
                                    .background(Primary, RoundedCornerShape(bottomStart = 4.dp, bottomEnd = 4.dp))
                            )
                        }
                        Text(
                            text = day,
                            style = MaterialTheme.typography.labelSmall,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LegendItem(text: String, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(color, CircleShape)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(text, style = MaterialTheme.typography.labelSmall)
    }
}

@Composable
fun InsightCards() {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            InsightCard(
                title = "Deep Sleep Rising",
                message = "You got 20% more deep sleep than your monthly average.",
                icon = Icons.Default.TrendingUp,
                iconColor = Primary
            )
        }
        item {
            InsightCard(
                title = "Consistent Bedtime",
                message = "Great job! You went to bed within your 30 min target window.",
                icon = Icons.Default.Bedtime,
                iconColor = Indigo
            )
        }
    }
}

@Composable
fun InsightCard(title: String, message: String, icon: androidx.compose.ui.graphics.vector.ImageVector, iconColor: Color) {
    Card(
        modifier = Modifier.width(280.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardDark)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = iconColor,
                modifier = Modifier
                    .size(40.dp)
                    .background(iconColor.copy(alpha = 0.1f), CircleShape)
                    .padding(8.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodySmall.copy(color = Color.White.copy(alpha = 0.7f)),
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

@Composable
fun StatsHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = { /* TODO: Navigate back */ }) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
        }
        Text("Sleep Statistics", style = MaterialTheme.typography.titleLarge)
        IconButton(onClick = { /* TODO: Share */ }) {
            Icon(Icons.Default.Share, contentDescription = "Share", tint = Primary)
        }
    }
}

@Composable
fun DateChips() {
    var selectedChip by remember { mutableStateOf("This Week") }
    val chips = listOf("This Week", "Last Week", "Custom")

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(chips.size) { index ->
            val isSelected = selectedChip == chips[index]
            Button(
                onClick = { selectedChip = chips[index] },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isSelected) Primary else CardDark,
                    contentColor = if (isSelected) Color.White else Color.White.copy(alpha = 0.7f)
                ),
                shape = RoundedCornerShape(50)
            ) {
                if (chips[index] == "Custom") {
                    Icon(
                        Icons.Default.CalendarMonth,
                        contentDescription = "Custom Date",
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                }
                Text(chips[index])
            }
        }
    }
}

@Composable
fun SleepScoreIndicator() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier.size(192.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawArc(
                    color = CardDark,
                    startAngle = -90f,
                    sweepAngle = 360f,
                    useCenter = false,
                    style = Stroke(width = 12.dp.toPx())
                )
                drawArc(
                    brush = Brush.linearGradient(colors = listOf(Primary, Indigo)),
                    startAngle = -90f,
                    sweepAngle = 360 * 0.85f, // 85% score
                    useCenter = false,
                    style = Stroke(width = 12.dp.toPx(), cap = StrokeCap.Round)
                )
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    "85",
                    style = MaterialTheme.typography.displayLarge.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    "Great Sleep",
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.White.copy(alpha = 0.6f))
                )
            }
        }
    }
}

// Old composables removed
