package com.example.schoolproject.data.utils

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RefreshDataWorker(
    context: Context,
    params: WorkerParameters
    // TODO: передать через Dagger
) : Worker(context, params) {

    override fun doWork() = try {
        CoroutineScope(Dispatchers.IO).launch {
//            syncInteract.syncTasks()
        }
        Log.d("tag", "success")
        Result.success()
    } catch (error: Throwable) {
        Log.d("tag", "fail")
        Result.failure()
    }
}
