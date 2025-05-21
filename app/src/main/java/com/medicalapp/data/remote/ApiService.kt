package com.medicalapp.data.remote

import com.medicalapp.data.model.User // Assuming User model is relevant for auth responses
import retrofit2.Response // Use Response for more control
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.GET
import retrofit2.http.Path

// Placeholder request/response data classes (can be expanded later)
data class LoginRequest(val email: String, val pass: String)
data class LoginResponse(val userId: String, val token: String, val role: String) // Role as String for now

data class RegistrationRequest(val email: String, val pass: String, val fullName: String, val role: String)
data class RegistrationResponse(val userId: String, val message: String)


interface ApiService {

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse> // Placeholder

    @POST("auth/register")
    suspend fun register(@Body request: RegistrationRequest): Response<RegistrationResponse> // Placeholder

    // Example of fetching data - to be defined more concretely later
    // @GET("doctors")
    // suspend fun getDoctors(): Response<List<DoctorProfile>>

    // @GET("patients/{id}")
    // suspend fun getPatientProfile(@Path("id") userId: String): Response<PatientProfile>

}
