package com.example.schoolproject

import android.app.Application
import com.example.schoolproject.data.NetworkRepositoryImpl
import com.example.schoolproject.data.TodoItemsRepositoryImpl
import com.example.schoolproject.data.network.ApiFactory
import com.example.schoolproject.data.network.ConnectionCheck
import com.example.schoolproject.data.network.TokenPreferences
import com.example.schoolproject.domain.SyncInteractor
import com.yandex.authsdk.YandexAuthToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ApplicationClass: Application() {
    override fun onCreate() {
        super.onCreate()

        if( ConnectionCheck(applicationContext).isNetworkAvailable() ) {
            val preferences = TokenPreferences(applicationContext)
            val token = preferences.getToken() ?: YandexAuthToken("", 0)
            ApiFactory.initialize(token)

            val repository = TodoItemsRepositoryImpl(applicationContext)
            val repositoryNetwork = NetworkRepositoryImpl(applicationContext)

            val syncInteractor = SyncInteractor(repository, repositoryNetwork)
            CoroutineScope(Dispatchers.Default).launch {
                syncInteractor.syncTasks()
            }
        }
    }
}