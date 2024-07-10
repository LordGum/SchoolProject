package com.example.schoolproject.data.utils

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RefreshDataWorker(
    context: Context,
    params: WorkerParameters,
    private val syncInteract: SyncInteractor
) : Worker(context, params) {

    override fun doWork() = try {
        CoroutineScope(Dispatchers.IO).launch {
            syncInteract.syncTasks()
        }
        Result.success()
    } catch (error: Throwable) {
        Result.failure()
    }
}
