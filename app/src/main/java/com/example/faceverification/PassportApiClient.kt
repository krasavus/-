package com.example.faceverification

import com.google.mlkit.vision.face.Face
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

data class VerificationResult(
    val isSuccess: Boolean,
    val message: String
)

class PassportApiClient {

    suspend fun verify(face: Face): VerificationResult {
        // Simulate network call
        withContext(Dispatchers.IO) {
            delay(2000)
        }

        // Return mock result
        return VerificationResult(
            isSuccess = true,
            message = "Verification successful. Name: John Doe, Passport ID: 123456789"
        )
    }
}
