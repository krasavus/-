package com.medicalapp.data.model

import java.util.Date

enum class AppointmentStatus {
    SCHEDULED,
    COMPLETED,
    CANCELLED,
    PENDING_PAYMENT // If payment is post-consultation
}

data class Appointment(
    val id: String,
    val patientId: String, // Foreign key to User (Patient)
    val doctorId: String,  // Foreign key to User (Doctor)
    val serviceId: String? = null, // Optional, if booking is for a specific service
    val appointmentDateTime: Date,
    val durationMinutes: Int,
    val notes: String? = null,
    val status: AppointmentStatus = AppointmentStatus.SCHEDULED,
    val paymentId: String? = null // Foreign key to PaymentDetails
)
