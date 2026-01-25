package com.example.sleepapp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen("home", "Home", Icons.Default.Home)
    object Stats : Screen("stats", "Stats", Icons.Default.Star)
    object Sounds : Screen("sounds", "Sounds", Icons.Default.Favorite)
    object Profile : Screen("profile", "Profile", Icons.Default.Person)
}
