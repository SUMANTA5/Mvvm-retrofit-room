package com.sumanta.myapplication.util

import android.app.Application
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.sumanta.myapplication.api.QuoteService
import com.sumanta.myapplication.api.RetrofitHelper
import com.sumanta.myapplication.db.QuoteDatabase
import com.sumanta.myapplication.repo.QuoteRepository
import com.sumanta.myapplication.workmanager.QuoteWorker
import kotlinx.coroutines.InternalCoroutinesApi
import java.util.concurrent.TimeUnit

class QuoteApplication : Application() {

    lateinit var quoteRepository: QuoteRepository

    @InternalCoroutinesApi
    override fun onCreate() {
        super.onCreate()
        initialize()
        setupWorker()
    }

    //WorkManager
    private fun setupWorker() {
        val constraint = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val workerRequest =
            PeriodicWorkRequest.Builder(QuoteWorker::class.java, 10, TimeUnit.MINUTES)
                .setConstraints(constraint)
                .build()
        WorkManager.getInstance(this).enqueue(workerRequest)
    }

    @InternalCoroutinesApi
    private fun initialize() {

        val quoteService = RetrofitHelper.getInstance().create(QuoteService::class.java)
        val database = QuoteDatabase.getDatabase(applicationContext)
        quoteRepository = QuoteRepository(quoteService, database, applicationContext)
    }
}