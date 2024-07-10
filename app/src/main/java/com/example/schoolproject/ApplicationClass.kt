package com.example.schoolproject

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import androidx.work.Configuration
import androidx.work.WorkManager
import com.example.schoolproject.data.network.ApiFactory
import com.example.schoolproject.data.network.TokenPreferences
import com.example.schoolproject.data.utils.InternetConnectionManager
import com.yandex.authsdk.YandexAuthToken

class ApplicationClass: Application(), Configuration.Provider  {

    override fun onCreate() {
        super.onCreate()
        WorkManager.initialize(this, Configuration.Builder().build())

        val connectionManager by lazy {
            InternetConnectionManager(
                connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager,
                workManager = WorkManager.getInstance(applicationContext)
            )
        }

        if(connectionManager.internetState.value) {
            val preferences = TokenPreferences(applicationContext)
            val token = preferences.getToken() ?: YandexAuthToken("", 0)
            ApiFactory.initialize(token)
            connectionManager.refreshIn8hours()
        }
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder().build()
    }
}