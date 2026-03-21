package com.example.tictactoemobile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tic_tac_toe_mobile.R
import com.example.tictactoemobile.di.AppModule
import com.example.tictactoemobile.di.DaggerAppComponent

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val component = DaggerAppComponent.builder().appModule(AppModule(applicationContext)).build()
        val repository = component.remoteDataSource()
        val dbService = component.getDatabaseService()
    }
}