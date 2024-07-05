package com.example.schoolproject

import android.app.Application
import com.example.schoolproject.data.network.ApiFactory
import com.example.schoolproject.data.network.TokenPreferences
import com.yandex.authsdk.YandexAuthToken

class ApplicationClass: Application() {
    override fun onCreate() {
        super.onCreate()

        val preferences = TokenPreferences(applicationContext)
        val token = preferences.getToken() ?: YandexAuthToken("", 0)
        ApiFactory.initialize(token)
    }
}