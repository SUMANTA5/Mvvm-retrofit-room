package com.sumanta.myapplication.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sumanta.myapplication.api.QuoteService
import com.sumanta.myapplication.models.QuoteList

class QuoteRepository(private var quoteService: QuoteService) {

    private val quoteLiveData = MutableLiveData<QuoteList>()

    val quotes: LiveData<QuoteList>
    get() = quoteLiveData

    suspend fun getQuotes(page: Int){
        val result = quoteService.getQuotes(page)
        if (result?.body() != null){
            quoteLiveData.postValue(result.body())
        }
    }
}