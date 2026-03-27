package com.example.tictactoemobile.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tic_tac_toe_mobile.R
import com.example.tictactoemobile.di.AppComponent
import com.example.tictactoemobile.di.AppModule
import com.example.tictactoemobile.di.DaggerAppComponent

class MainActivity : AppCompatActivity() {
    lateinit var appComponent: AppComponent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        appComponent = DaggerAppComponent.builder().appModule(AppModule(applicationContext)).build()
        val repository = appComponent.remoteDataSource()
        val dbService = appComponent.getDatabaseService()
        setContentView(R.layout.activity_main)
    }
}