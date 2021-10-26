package com.sumanta.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sumanta.myapplication.api.QuoteService
import com.sumanta.myapplication.api.RetrofitHelper
import com.sumanta.myapplication.repo.QuoteRepository
import com.sumanta.myapplication.viewmodel.MainViewModel
import com.sumanta.myapplication.viewmodel.MainViewModelFactory
import retrofit2.create

class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val quoteService = RetrofitHelper.getInstance().create(QuoteService::class.java)
        val repository = QuoteRepository(quoteService)

        mainViewModel =
            ViewModelProvider(this, MainViewModelFactory(repository)).get(MainViewModel::class.java)

        mainViewModel.quote.observe(this, Observer {
            Log.d("Sumanta", it.results.toString())
        })
    }
}