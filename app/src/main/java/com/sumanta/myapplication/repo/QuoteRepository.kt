package com.sumanta.myapplication.repo

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sumanta.myapplication.api.QuoteService
import com.sumanta.myapplication.db.QuoteDatabase
import com.sumanta.myapplication.models.QuoteList
import com.sumanta.myapplication.util.NetworkUtils
import java.lang.Exception

class QuoteRepository(
    private var quoteService: QuoteService,
    private val quoteDatabase: QuoteDatabase,
    private val applicationContext: Context
) {

    //error handling
    private val quoteLiveData = MutableLiveData<Response<QuoteList>>()

    //error handling
    val quotes: LiveData<Response<QuoteList>>
        get() = quoteLiveData

    suspend fun getQuotes(page: Int) {
        // internet access on android
        if (NetworkUtils.isInternetAvailable(applicationContext)){
           //error handling
            try {
                val result = quoteService.getQuotes(page)
                if (result?.body() != null) {
                    quoteDatabase.quoteDao().addQuotes(result.body()!!.results)
                    quoteLiveData.postValue(Response.Success(result.body()))
                }else{
                    quoteLiveData.postValue(Response.Error("API Error"))
                }
            }
            catch (e: Exception){
                quoteLiveData.postValue(Response.Error(e.message.toString()))
            }

        }else{
            val quote = quoteDatabase.quoteDao().getQuotes()
            val quoteList = QuoteList(1,1,1,quote,1,1)
            quoteLiveData.postValue(Response.Success(quoteList))
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