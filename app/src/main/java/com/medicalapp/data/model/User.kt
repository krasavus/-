package com.medicalapp.data.model

import java.util.Date

enum class UserRole {
    PATIENT,
    DOCTOR
}

data class User(
    val id: String,
    val email: String,
    val role: UserRole,
    val fullName: String,
    val phoneNumber: String? = null,
    val profileImageUrl: String? = null,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
    // Password hash should not be stored in the User object handled by the client.
    // It's managed by the auth system/backend.
)
