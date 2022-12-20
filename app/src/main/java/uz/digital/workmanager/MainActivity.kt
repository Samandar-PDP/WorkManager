package uz.digital.workmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.work.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import uz.digital.workmanager.manager.EmotionalWorker
import uz.digital.workmanager.manager.MyWorkManager
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //myWorkManager()
        emotionalWorker()

    }

    private fun emotionalWorker() {
        val btn: Button = findViewById(R.id.btn)
        val editText: EditText = findViewById(R.id.editText)
        val textView: TextView = findViewById(R.id.textView)
        val workManager = WorkManager.getInstance(this)
        btn.setOnClickListener {
            val text = editText.text.toString().trim()
            val data = Data.Builder()
                .putString("my_key", text)
                .build()

            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresCharging(true)
                .build()
            val emotionalWorker = OneTimeWorkRequestBuilder<EmotionalWorker>()
                .setConstraints(constraints)
                .setInputData(data)
                .build()

            workManager.enqueue(emotionalWorker)

            workManager.getWorkInfoByIdLiveData(emotionalWorker.id)
                .observe(this) {
                    textView.text = it.state.name
                    if (it.state == WorkInfo.State.SUCCEEDED) {
                        val result = it.outputData.getString("output_key") ?: "Empty"
                        Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun myWorkManager() {
        //        val constraints = Constraints.Builder()
//            .setRequiredNetworkType(NetworkType.CONNECTED)
//            .setRequiresCharging(true)
//            .build()
//
//        val workRequest = OneTimeWorkRequestBuilder<MyWorkManager>()
//            .setInitialDelay(2, TimeUnit.MINUTES)
//            .setConstraints(constraints)
//            .build()
//
//        WorkManager.getInstance(this).enqueue(workRequest)

    }
}