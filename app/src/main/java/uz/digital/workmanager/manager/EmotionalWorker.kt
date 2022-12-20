package uz.digital.workmanager.manager

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import kotlinx.coroutines.delay

class EmotionalWorker(
    context: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {
    private val TAG = "EmotionalWorker"
    override suspend fun doWork(): Result {
        val text = inputData.getString("my_key").toString()
        try {
            for (i in 0..20) {
                Log.d(TAG, "doWork: $i")

                delay(500L)

                val outputData = Data.Builder()
                    .putString("output_key", randomText(text))
                    .build()
                return Result.success(outputData)
            }
        } catch (e: Exception) {
            Log.d(TAG, "doWork: ${e.message}")
            return Result.failure()
        }
        return Result.retry()
    }

    private fun randomText(text: String): String {
        return listOf(
            "Man City",
            "Liverpool",
            "Tottenham",
            "Man UTD",
            "Chelsea",
            "Arsenal"
        ).random()
    }
}