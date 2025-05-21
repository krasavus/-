package com.medicalapp.ui.patient

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.medicalapp.ui.theme.MedicalAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientDashboardScreen() {
    // val navController = rememberNavController() // If this screen has its own internal navigation
    // val userViewModel: UserViewModel = koinViewModel() // Example if fetching user data

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Patient Dashboard") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp), // Additional padding for content
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Welcome, Patient!",
                style = MaterialTheme.typography.headlineMedium
            )
            // Add more patient-specific UI elements here later
            // e.g., Search for doctors, view appointments, etc.
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PatientDashboardScreenPreview() {
    MedicalAppTheme {
        PatientDashboardScreen()
    }
}
