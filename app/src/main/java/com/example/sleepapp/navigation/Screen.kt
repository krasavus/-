package com.example.sleepapp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.LibraryMusic

sealed class Screen(val route: String, val title: String? = null, val icon: ImageVector? = null) {
    object Home : Screen("home", "Home", Icons.Default.Home)
    object Stats : Screen("stats", "Stats", Icons.Default.BarChart)
    object Library : Screen("library", "Library", Icons.Default.LibraryMusic)
    object Luna : Screen("luna", "Luna", Icons.Default.AutoAwesome)

    // Screens without bottom nav items
    object HabitJournal : Screen("journal")
    object AlarmSettings : Screen("alarm")
    object AiConsultant : Screen("ai_consultant")
    object Profile : Screen("profile")
}
