package com.example.tictactoemobile.presentation.ui

import android.app.Application
import androidx.room.Room
import com.example.tic_tac_toe_mobile.data.db.AppDatabase

class TicTacToeApp  : Application() {

    lateinit var database: AppDatabase
        private set

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "tic_tac_toe.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}