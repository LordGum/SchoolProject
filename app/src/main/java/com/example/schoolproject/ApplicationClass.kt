package com.example.schoolproject

import android.app.Application
import com.example.schoolproject.data.network.ApiFactory
import com.example.schoolproject.data.network.TokenPreferences

class ApplicationClass: Application() {
    override fun onCreate() {
        super.onCreate()

        val preferences = TokenPreferences(applicationContext)
        val token = preferences.getToken() ?: throw RuntimeException("token is null")
        ApiFactory.initialize(token)
    }
}