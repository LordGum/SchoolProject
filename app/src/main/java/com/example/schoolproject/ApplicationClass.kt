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

class ApplicationClass: Application()  {

    override fun onCreate() {
        super.onCreate()

        val configuration = Configuration.Builder().build()
        WorkManager.initialize(this, configuration)

        val connectionManager by lazy {
            InternetConnectionManager(
                connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager,
                workManager = WorkManager.getInstance(this)
            )
        }

        if(connectionManager.internetState.value) {
            val preferences = TokenPreferences(applicationContext)
            val token = preferences.getToken() ?: YandexAuthToken("", 0)
            ApiFactory.initialize(token)
            connectionManager.refreshIn8hours()
        }
    }
}