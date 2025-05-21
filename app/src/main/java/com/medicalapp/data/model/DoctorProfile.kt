package com.medicalapp.data.model

data class DoctorProfile(
    val userId: String, // Foreign key to User
    val specialization: String,
    val qualifications: List<String>,
    val experienceYears: Int,
    val consultationFee: Double,
    val bio: String? = null,
    val availableSlots: List<String> = emptyList() // e.g., "MON_1000_1030" (Monday 10:00-10:30)
)
