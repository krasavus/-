package com.example.sleepapp.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.WatchLater
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.sleepapp.navigation.Screen
import com.example.sleepapp.ui.theme.*

@Composable
fun HomeScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(MidnightStart, MidnightEnd)
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Header(navController)
            Spacer(modifier = Modifier.height(48.dp))
            CircularCountdown()
            Spacer(modifier = Modifier.height(48.dp))
            PrimaryActionButton(navController)
            Spacer(modifier = Modifier.height(48.dp))
            MiniStats()
        }
    }
}

@Composable
fun CircularCountdown() {
    Box(
        modifier = Modifier.size(256.dp),
        contentAlignment = Alignment.Center
    ) {
        // Outer glow
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(Primary.copy(alpha = 0.3f), Color.Transparent),
                    radius = size.width / 2
                ),
                radius = size.width / 2
            )
        }

        // Background Ring
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawArc(
                color = CardDark,
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = 12.dp.toPx())
            )
        }

        // Progress Ring
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawArc(
                color = Primary,
                startAngle = -90f,
                sweepAngle = 270f, // Example progress
                useCenter = false,
                style = Stroke(width = 12.dp.toPx(), cap = StrokeCap.Round)
            )
        }

        // Text Content
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Default.Bedtime,
                contentDescription = "Bedtime",
                tint = Primary.copy(alpha = 0.8f),
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontSize = 40.sp, fontWeight = FontWeight.Bold)) {
                        append("6")
                    }
                    withStyle(style = SpanStyle(fontSize = 24.sp, color = Color.White.copy(alpha = 0.6f))) {
                        append("h")
                    }
                    withStyle(style = SpanStyle(fontSize = 40.sp, fontWeight = FontWeight.Bold)) {
                        append(" 30")
                    }
                    withStyle(style = SpanStyle(fontSize = 24.sp, color = Color.White.copy(alpha = 0.6f))) {
                        append("m")
                    }
                }
            )
            Text(
                text = "Until 7:00 AM Alarm",
                style = MaterialTheme.typography.bodySmall.copy(color = Color.White.copy(alpha = 0.5f)),
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Composable
fun PrimaryActionButton(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .height(56.dp)
            .clip(CircleShape)
            .background(Primary)
            .clickable { navController.navigate(Screen.HabitJournal.route) }
            .shadow(
                elevation = 20.dp,
                spotColor = Primary,
                ambientColor = Primary
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Start Sleep Tracking",
                tint = Color.White
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Start Sleep Tracking",
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
            )
        }
    }
}

@Composable
fun GlassmorphismCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .background(Color.White.copy(alpha = 0.05f))
            .border(
                width = 1.dp,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.2f),
                        Color.Transparent
                    )
                ),
                shape = RoundedCornerShape(24.dp)
            )
    ) {
        content()
    }
}

@Composable
fun MiniStats() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        GlassmorphismCard(modifier = Modifier.weight(1f)) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.BarChart,
                        contentDescription = "Sleep Score",
                        tint = Emerald,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "SLEEP SCORE",
                        style = MaterialTheme.typography.labelSmall.copy(color = Color.White.copy(alpha = 0.6f))
                    )
                }
                Row(
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = "85",
                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "+2%",
                        style = MaterialTheme.typography.bodySmall.copy(color = Emerald, fontWeight = FontWeight.SemiBold)
                    )
                }
            }
        }
        GlassmorphismCard(modifier = Modifier.weight(1f)) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.WatchLater,
                        contentDescription = "Hours Slept",
                        tint = Indigo,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "HOURS SLEPT",
                        style = MaterialTheme.typography.labelSmall.copy(color = Color.White.copy(alpha = 0.6f))
                    )
                }
                Row(
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = "7h 12m",
                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Avg",
                        style = MaterialTheme.typography.bodySmall.copy(color = Indigo, fontWeight = FontWeight.SemiBold)
                    )
                }
            }
        }
    }
}

@Composable
fun Header(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "Good Evening,",
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.White.copy(alpha = 0.7f))
            )
            Text(
                text = "Alex",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold)
            )
        }
        IconButton(
            onClick = { navController.navigate(Screen.AlarmSettings.route) },
            modifier = Modifier
                .size(40.dp)
                .background(Color.White.copy(alpha = 0.05f), RoundedCornerShape(50))
        ) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Settings",
                tint = Color.White.copy(alpha = 0.8f)
            )
        }
    }
}

// Old composables removed
