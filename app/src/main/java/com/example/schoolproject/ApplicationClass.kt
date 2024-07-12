package com.example.schoolproject

import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager
import com.example.schoolproject.di.DaggerApplicationComponent

class ApplicationClass : Application(), Configuration.Provider  {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }

    override fun onCreate() {
        super.onCreate()

        val configuration = Configuration.Builder().build()
        WorkManager.initialize(this, configuration)
    }

    override fun getWorkManagerConfiguration(): Configuration = Configuration.Builder().build()
}
