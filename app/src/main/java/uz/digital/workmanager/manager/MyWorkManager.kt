package uz.digital.workmanager.manager

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import uz.digital.workmanager.network.RetrofitInstance

class MyWorkManager(
    context: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(
    context,
    workerParameters
) {
    private val TAG = "MyWorkManager"
    override suspend fun doWork(): Result {
        Log.d(TAG, "doWork: started")
        return getUsers()
    }

    private suspend fun getUsers(): Result {
        Log.d(TAG, "getUsers: trying")
        return try {
            val response = RetrofitInstance.provideUserApi().getUsers()
            if (response.isSuccessful) {
                Log.d(TAG, "getUsers: ${response.body()}")
                Result.success(workDataOf("data" to response.body()))
            } else {
                Log.d(TAG, "getUsers: retry")
                Result.retry()
            }
        } catch (e: Exception) {
            Log.d(TAG, "getUsers: ${e.message}")
            Result.failure(workDataOf("error" to e.message.toString()))
        }
    }
}