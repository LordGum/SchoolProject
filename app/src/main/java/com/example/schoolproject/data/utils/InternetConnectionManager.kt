package com.example.schoolproject.data.utils

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.util.Log
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.concurrent.TimeUnit

class InternetConnectionManager(
    private val connectivityManager: ConnectivityManager,
    private val workManager: WorkManager
) {

    private val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    private val _internetState: MutableStateFlow<Boolean> = MutableStateFlow(isNetworkAvailable())
    val internetState = _internetState.asStateFlow()

    init {
        checkNetworkConnection()
    }

    private fun isNetworkAvailable(): Boolean {
        val networkCapabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }

    fun refreshOneTime() {
        Log.d("tag", "in refresh")
        val request = OneTimeWorkRequest.Builder(
            RefreshDataWorker::class.java,
        ).setConstraints(constraints).build()

        workManager.enqueueUniqueWork(
            ONCE_UPDATE_WORKER_NAME,
            ExistingWorkPolicy.KEEP,
            request
        )
    }

    fun refreshIn8hours() {
        val periodicWorkRequest = PeriodicWorkRequest.Builder(
            RefreshDataWorker::class.java,
            PERIOD_TIME, TimeUnit.HOURS
        ).setConstraints(constraints).build()

        workManager.enqueueUniquePeriodicWork(
            PERIOD_WORKER_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            periodicWorkRequest
        )
    }

    private fun checkNetworkConnection() {
        connectivityManager.registerDefaultNetworkCallback(object :
            ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                _internetState.update { true }
                refreshOneTime()
            }

            override fun onLost(network: Network) {
                _internetState.update { false }
            }
        })
    }

    companion object {
        private const val PERIOD_TIME = 8L
        private const val PERIOD_WORKER_NAME = "Period worker name"
        private const val ONCE_UPDATE_WORKER_NAME = "Once worker name"
    }
}