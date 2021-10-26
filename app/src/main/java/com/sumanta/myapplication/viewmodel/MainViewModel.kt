package com.sumanta.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sumanta.myapplication.models.QuoteList
import com.sumanta.myapplication.repo.QuoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: QuoteRepository): ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getQuotes(1)
        }
    }

    val quote: LiveData<QuoteList>
    get() = repository.quotes

}