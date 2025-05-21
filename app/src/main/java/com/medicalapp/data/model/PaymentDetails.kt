package com.medicalapp.data.model

import java.util.Date

enum class PaymentStatus {
    PENDING,
    SUCCESSFUL,
    FAILED
}

data class PaymentDetails(
    val id: String,
    val appointmentId: String, // Foreign key to Appointment
    val amount: Double,
    val paymentMethod: String, // e.g., "Credit Card", "PayPal"
    val transactionId: String? = null, // From payment gateway
    val status: PaymentStatus = PaymentStatus.PENDING,
    val paymentDate: Date = Date()
)
