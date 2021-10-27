package com.sumanta.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sumanta.myapplication.repo.Response
import com.sumanta.myapplication.util.QuoteApplication
import com.sumanta.myapplication.viewmodel.MainViewModel
import com.sumanta.myapplication.viewmodel.MainViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository = (application as QuoteApplication).quoteRepository
        mainViewModel =
            ViewModelProvider(this, MainViewModelFactory(repository)).get(MainViewModel::class.java)

        mainViewModel.quote.observe(this, Observer {
           //error handling
            when(it){
                is Response.Loading ->{}
                is Response.Success ->{
                    it.data?.let {
                        Toast.makeText(this,it.results.size.toString(), Toast.LENGTH_LONG).show()
                    }
                }
                is Response.Error ->{
                    Toast.makeText(this,"Some Error", Toast.LENGTH_LONG).show()
                }
            }
        })
    }
}