package com.medicalapp.data.model

data class Service(
    val id: String,
    val doctorId: String, // Foreign key to User (Doctor)
    val name: String,
    val description: String,
    val durationMinutes: Int,
    val price: Double
)
