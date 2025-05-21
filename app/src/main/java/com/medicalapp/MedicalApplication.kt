package com.medicalapp

import android.app.Application
import com.medicalapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MedicalApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            // Use Koin Android Logger (Level.ERROR to see errors only, Level.INFO for more details)
            androidLogger(Level.INFO) 
            // Reference Android context
            androidContext(this@MedicalApplication)
            // Load modules
            modules(appModule)
        }
    }
}
