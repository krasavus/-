package com.medicalapp.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.medicalapp.data.model.UserRole
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// Basic Login/Registration State
sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Authenticated(val userId: String, val role: UserRole) : AuthState()
    data class Error(val message: String) : AuthState()
}

class AuthViewModel : ViewModel() { // Koin will provide this later

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    private val _registrationState = MutableStateFlow<AuthState>(AuthState.Idle)
    val registrationState: StateFlow<AuthState> = _registrationState

    fun login(email: String, pass: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            // Simulate network call
            kotlinx.coroutines.delay(1000)
            if (email.isNotEmpty() && pass.isNotEmpty()) {
                // In a real app, this would involve a repository call to a backend
                // For now, mock success, assuming a patient role for simplicity
                _authState.value = AuthState.Authenticated("mockUserId", UserRole.PATIENT)
            } else {
                _authState.value = AuthState.Error("Invalid credentials")
            }
        }
    }

    fun register(email: String, pass: String, fullName: String, role: UserRole) {
        viewModelScope.launch {
            _registrationState.value = AuthState.Loading
            // Simulate network call
            kotlinx.coroutines.delay(1500)
            if (email.isNotEmpty() && pass.isNotEmpty() && fullName.isNotEmpty()) {
                 // Mock success
                _registrationState.value = AuthState.Authenticated("newMockUserId", role)
            } else {
                _registrationState.value = AuthState.Error("All fields are required")
            }
        }
    }

    fun resetAuthState() {
        _authState.value = AuthState.Idle
    }

    fun resetRegistrationState() {
        _registrationState.value = AuthState.Idle
    }
}
