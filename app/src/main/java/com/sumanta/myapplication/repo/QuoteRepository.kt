package com.sumanta.myapplication.repo

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sumanta.myapplication.api.QuoteService
import com.sumanta.myapplication.db.QuoteDatabase
import com.sumanta.myapplication.models.QuoteList
import com.sumanta.myapplication.util.NetworkUtils

class QuoteRepository(
    private var quoteService: QuoteService,
    private val quoteDatabase: QuoteDatabase,
    private val applicationContext: Context
) {

    private val quoteLiveData = MutableLiveData<QuoteList>()

    val quotes: LiveData<QuoteList>
        get() = quoteLiveData

    suspend fun getQuotes(page: Int) {
        // internet access on android
        if (NetworkUtils.isInternetAvailable(applicationContext)){
            val result = quoteService.getQuotes(page)
            if (result?.body() != null) {
                quoteDatabase.quoteDao().addQuotes(result.body()!!.results)
                quoteLiveData.postValue(result.body())
            }
        }else{
            val quote = quoteDatabase.quoteDao().getQuotes()
            val quoteList = QuoteList(1,1,1,quote,1,1)
            quoteLiveData.postValue(quoteList)
        }
    }

    //WorkManager
    suspend fun getQuotesBackground(){
        val randomNumber = (Math.random() * 10).toInt()
        val result = quoteService.getQuotes(randomNumber)
        if (result?.body() != null){
            quoteDatabase.quoteDao().addQuotes(result.body()!!.results)
        }
    }
}