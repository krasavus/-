package com.medicalapp.auth.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.medicalapp.data.model.UserRole
import com.medicalapp.auth.viewmodel.AuthViewModel
import com.medicalapp.auth.viewmodel.AuthState // Explicit import
import com.medicalapp.ui.theme.MedicalAppTheme
import org.koin.androidx.compose.koinViewModel // Import Koin's Composable VM injector

@Composable
fun RegistrationScreen(
    authViewModel: AuthViewModel = koinViewModel(), // Use Koin to inject ViewModel
    onRegistrationSuccess: (String, UserRole) -> Unit,
    onNavigateToLogin: () -> Unit
) {
    val registrationState by authViewModel.registrationState.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var fullName by remember { mutableStateOf("") }
    val roles = UserRole.values() 
    var selectedRole by remember { mutableStateOf(UserRole.PATIENT) }
    var showError by remember { mutableStateOf<String?>(null) }
    var passwordMismatchError by remember { mutableStateOf(false) }

    LaunchedEffect(registrationState) {
        when (val state = registrationState) {
            is AuthState.Authenticated -> { // Use imported AuthState
                onRegistrationSuccess(state.userId, state.role)
                authViewModel.resetRegistrationState()
            }
            is AuthState.Error -> { // Use imported AuthState
                showError = state.message
                authViewModel.resetRegistrationState()
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
        Text("Register", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(value = fullName, onValueChange = { fullName = it }, label = { Text("Full Name") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Password") }, visualTransformation = PasswordVisualTransformation(), modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = {
                confirmPassword = it
                passwordMismatchError = password != it
            },
            label = { Text("Confirm Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            isError = passwordMismatchError
        )
        if (passwordMismatchError) {
             Text("Passwords do not match", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
        }
        Spacer(modifier = Modifier.height(16.dp))

        Text("Register as:", style = MaterialTheme.typography.labelLarge)
        Row(Modifier.fillMaxWidth()) {
            roles.forEach { role ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = (role == selectedRole),
                        onClick = { selectedRole = role }
                    )
                    Text(text = role.name, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(start = 4.dp))
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        if (registrationState is AuthState.Loading) { // Use imported AuthState
            CircularProgressIndicator()
        } else {
            Button(
                onClick = {
                    if (password != confirmPassword) {
                        passwordMismatchError = true
                    } else {
                        passwordMismatchError = false
                        authViewModel.register(email, password, fullName, selectedRole)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !passwordMismatchError
            ) {
                Text("Register")
            }
        }

        showError?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(it, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(8.dp))
        TextButton(onClick = onNavigateToLogin) {
            Text("Already have an account? Login")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegistrationScreenPreview() {
    MedicalAppTheme {
        RegistrationScreen(
            authViewModel = AuthViewModel(), // Provide a dummy for preview
            onRegistrationSuccess = {_,_ ->}, 
            onNavigateToLogin = {}
        )
    }
}
