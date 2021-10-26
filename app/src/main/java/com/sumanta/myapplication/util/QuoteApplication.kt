package com.sumanta.myapplication.util

import android.app.Application
import com.sumanta.myapplication.api.QuoteService
import com.sumanta.myapplication.api.RetrofitHelper
import com.sumanta.myapplication.db.QuoteDatabase
import com.sumanta.myapplication.repo.QuoteRepository
import kotlinx.coroutines.InternalCoroutinesApi

class QuoteApplication: Application() {

    lateinit var quoteRepository: QuoteRepository
    @InternalCoroutinesApi
    override fun onCreate() {
        super.onCreate()
        initialize()
    }

    @InternalCoroutinesApi
    private fun initialize() {

        val quoteService = RetrofitHelper.getInstance().create(QuoteService::class.java)
        val database = QuoteDatabase.getDatabase(applicationContext)
         quoteRepository = QuoteRepository(quoteService, database,applicationContext)
    }
}