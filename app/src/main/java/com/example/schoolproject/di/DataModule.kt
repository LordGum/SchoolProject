package com.example.schoolproject.di

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import androidx.work.WorkManager
import com.example.schoolproject.data.NetworkRepositoryImpl
import com.example.schoolproject.data.TodoItemsRepositoryImpl
import com.example.schoolproject.data.database.AppDatabase
import com.example.schoolproject.data.database.TodoListDao
import com.example.schoolproject.data.network.ApiFactory
import com.example.schoolproject.data.network.ApiService
import com.example.schoolproject.domain.NetworkRepository
import com.example.schoolproject.domain.TodoItemsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {
    @Binds
    fun bindNetworkRepository(impl: NetworkRepositoryImpl): NetworkRepository

    @Binds
    fun bindDatabaseRepository(impl: TodoItemsRepositoryImpl): TodoItemsRepository


    companion object {
        @[ApplicationScope Provides]
        fun provideToDoDao(
            application: Application
        ): TodoListDao {
            return AppDatabase.getInstance(application).todoDao()
        }

        @[ApplicationScope Provides]
        fun provideApiService(): ApiService = ApiFactory.apiService

        @[ApplicationScope Provides]
        fun provideConnectivityManager(
            application: Application
        ): ConnectivityManager {
            return application.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        }

        @[ApplicationScope Provides]
        fun provideWorkManager(
            application: Application
        ): WorkManager {
            return WorkManager.getInstance(application.applicationContext)
        }
    }
}