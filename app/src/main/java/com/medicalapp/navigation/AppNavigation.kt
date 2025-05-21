package com.medicalapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.medicalapp.auth.ui.LoginScreen
import com.medicalapp.auth.ui.RegistrationScreen
import com.medicalapp.data.model.UserRole
import com.medicalapp.ui.doctor.DoctorDashboardScreen // Will be created in next step
import com.medicalapp.ui.patient.PatientDashboardScreen // Will be created in next step


@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.LoginScreen.route) {
        composable(Screen.LoginScreen.route) {
            LoginScreen(
                onLoginSuccess = { userId, role ->
                    // Navigate to the appropriate dashboard based on role
                    val destination = if (role == UserRole.DOCTOR) {
                        Screen.DoctorDashboardScreen.route
                    } else {
                        Screen.PatientDashboardScreen.route
                    }
                    // Include userId as an argument if dashboards need it directly,
                    // or rely on a shared ViewModel/session manager later.
                    // For now, just navigate.
                    navController.navigate(destination) {
                        popUpTo(Screen.LoginScreen.route) { inclusive = true } // Clear login from back stack
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(Screen.RegistrationScreen.route)
                }
            )
        }
        composable(Screen.RegistrationScreen.route) {
            RegistrationScreen(
                onRegistrationSuccess = { userId, role ->
                    val destination = if (role == UserRole.DOCTOR) {
                        Screen.DoctorDashboardScreen.route
                    } else {
                        Screen.PatientDashboardScreen.route
                    }
                    navController.navigate(destination) {
                        popUpTo(Screen.RegistrationScreen.route) { inclusive = true }
                        popUpTo(Screen.LoginScreen.route) { inclusive = true } // Also clear login
                    }
                },
                onNavigateToLogin = {
                    navController.popBackStack() // Go back to Login screen
                }
            )
        }
        composable(Screen.PatientDashboardScreen.route) {
            // PatientDashboardScreen will be created in the next step
            // For now, we can use a placeholder if PatientDashboardScreen is not yet available
            // PatientDashboardScreen() 
            // To avoid compilation error, ensure PatientDashboardScreen is available or use a placeholder
             PatientDashboardScreen()
        }
        composable(Screen.DoctorDashboardScreen.route) {
            // DoctorDashboardScreen will be created in the next step
            // DoctorDashboardScreen()
            // To avoid compilation error, ensure DoctorDashboardScreen is available or use a placeholder
             DoctorDashboardScreen()
        }
    }
}
