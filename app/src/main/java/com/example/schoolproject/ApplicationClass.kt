package com.example.schoolproject

import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager

class ApplicationClass : Application(), Configuration.Provider  {

    override fun onCreate() {
        super.onCreate()

        val configuration = Configuration.Builder().build()
        WorkManager.initialize(this, configuration)
    }

    override fun getWorkManagerConfiguration(): Configuration = Configuration.Builder().build()
}