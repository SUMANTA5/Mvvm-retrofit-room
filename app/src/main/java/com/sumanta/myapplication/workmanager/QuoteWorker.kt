package com.sumanta.myapplication.workmanager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.sumanta.myapplication.util.QuoteApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//WorkManager
class QuoteWorker(private val context: Context, params: WorkerParameters) :
    Worker(context, params) {
    override fun doWork(): Result {
        Log.d("Sumanta","WorkManager sumanta")
        val repository =(context as QuoteApplication).quoteRepository
        CoroutineScope(Dispatchers.IO).launch {
            repository.getQuotesBackground()
        }
        return Result.success()
    }
}