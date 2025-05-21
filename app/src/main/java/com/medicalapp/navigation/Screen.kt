package com.medicalapp.navigation

sealed class Screen(val route: String) {
    object LoginScreen : Screen("login_screen")
    object RegistrationScreen : Screen("registration_screen")
    object PatientDashboardScreen : Screen("patient_dashboard_screen")
    object DoctorDashboardScreen : Screen("doctor_dashboard_screen")
    // Add more screens here as needed
}
