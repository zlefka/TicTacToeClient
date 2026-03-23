package com.example.tictactoemobile.di

import com.example.tic_tac_toe_mobile.data.db.service.DatabaseService
import com.example.tic_tac_toe_mobile.data.repository.RemoteDataSource
import com.example.tic_tac_toe_mobile.di.GameApiModule
import com.example.tictactoemobile.data.repository.AuthRepository
import com.example.tictactoemobile.data.repository.GameRepository
import com.example.tictactoemobile.data.repository.UserRepository
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, GameApiModule::class, DatabaseModule::class, RepositoryModule::class])
interface AppComponent {
    fun getDatabaseService(): DatabaseService
    fun remoteDataSource(): RemoteDataSource
    fun getGameRepository(): GameRepository
    fun getAuthRepository(): AuthRepository
    fun getUserRepository(): UserRepository
}