package com.medicalapp.auth.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.medicalapp.auth.viewmodel.AuthViewModel
import com.medicalapp.auth.viewmodel.AuthState // Explicit import
import com.medicalapp.ui.theme.MedicalAppTheme
import org.koin.androidx.compose.koinViewModel // Import Koin's Composable VM injector

@Composable
fun LoginScreen(
    authViewModel: AuthViewModel = koinViewModel(), // Use Koin to inject ViewModel
    onLoginSuccess: (String, com.medicalapp.data.model.UserRole) -> Unit,
    onNavigateToRegister: () -> Unit
) {
    val authState by authViewModel.authState.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(authState) {
        when (val state = authState) {
            is AuthState.Authenticated -> { // Use imported AuthState
                onLoginSuccess(state.userId, state.role)
                authViewModel.resetAuthState()
            }
            is AuthState.Error -> { // Use imported AuthState
                showError = state.message
                authViewModel.resetAuthState()
            }
            AuthState.Loading -> { // Use imported AuthState
                showError = null
            }
            else -> Unit
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Login", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        if (authState is AuthState.Loading) { // Use imported AuthState
            CircularProgressIndicator()
        } else {
            Button(
                onClick = { authViewModel.login(email, password) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Login")
            }
        }

        showError?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(it, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(8.dp))
        TextButton(onClick = onNavigateToRegister) {
            Text("Don't have an account? Register")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    MedicalAppTheme {
        // For preview, Koin isn't typically running.
        // We might need a wrapper or a way to provide a mock AuthViewModel for previews
        // if direct koinViewModel() causes issues in preview mode.
        // For now, this might not render the ViewModel part in preview correctly.
        LoginScreen(
            authViewModel = AuthViewModel(), // Provide a dummy for preview
            onLoginSuccess = {_,_ -> }, 
            onNavigateToRegister = {}
        )
    }
}
