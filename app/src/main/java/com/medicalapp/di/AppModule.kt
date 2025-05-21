package com.medicalapp.di

import com.medicalapp.auth.viewmodel.AuthViewModel
import com.medicalapp.data.remote.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {
    // ViewModels
    viewModel { AuthViewModel(/* get() if ApiService is injected */) } // Pass ApiService if AuthViewModel needs it

    // Network Client (OkHttp)
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY // Log request and response bodies
            })
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    // Retrofit Instance
    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl("https://dummy.restapiexample.com/api/v1/") // Placeholder Base URL
            .client(get()) // Inject OkHttpClient
            .addConverterFactory(GsonConverterFactory.create()) // Use Gson for JSON parsing
            .build()
    }

    // ApiService
    single<ApiService> {
        get<Retrofit>().create(ApiService::class.java) // Create ApiService instance from Retrofit
    }

    // Repositories (will be added later, e.g., AuthRepository that uses ApiService)
    // e.g., single<AuthRepository> { AuthRepositoryImpl(get()) }
}
