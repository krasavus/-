package com.medicalapp.data.model

import java.util.Date

data class PatientProfile(
    val userId: String, // Foreign key to User
    val dateOfBirth: Date? = null,
    val bloodGroup: String? = null,
    val medicalHistory: List<String> = emptyList() // Brief summary points
)
