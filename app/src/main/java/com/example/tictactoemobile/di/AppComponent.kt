package com.example.tictactoemobile.di

import com.example.tic_tac_toe_mobile.data.db.service.DatabaseService
import com.example.tic_tac_toe_mobile.data.repository.GameRepository
import com.example.tic_tac_toe_mobile.di.GameApiModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, GameApiModule::class, DatabaseModule::class])
interface AppComponent {
    fun getDatabaseService(): DatabaseService
    fun getGameRepository(): GameRepository
}