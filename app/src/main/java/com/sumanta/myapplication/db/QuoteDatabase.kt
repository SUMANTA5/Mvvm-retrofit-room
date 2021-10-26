package com.sumanta.myapplication.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sumanta.myapplication.models.Result
import kotlinx.coroutines.InternalCoroutinesApi

@Database(entities = [Result::class],version = 1)
abstract class QuoteDatabase : RoomDatabase(){

    abstract fun quoteDao(): QuoteDao

    companion object{
        @Volatile
        private var INSTANCE : QuoteDatabase? = null

        @InternalCoroutinesApi
        fun getDatabase(context: Context) : QuoteDatabase{
            if (INSTANCE == null){
                synchronized(this){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        QuoteDatabase::class.java,
                        "contactDB"
                    ).build()
                }
            }
            return INSTANCE!!
        }
    }
}