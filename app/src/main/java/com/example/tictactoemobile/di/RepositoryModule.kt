package com.example.tictactoemobile.di

import com.example.tic_tac_toe_mobile.data.db.service.DatabaseService
import com.example.tic_tac_toe_mobile.data.repository.RemoteDataSource
import com.example.tictactoemobile.data.api.SessionManager
import com.example.tictactoemobile.data.repository.AuthRepository
import com.example.tictactoemobile.data.repository.GameRepository
import com.example.tictactoemobile.data.repository.UserRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun provideGameRepository(remoteDataSource: RemoteDataSource, databaseService: DatabaseService) = GameRepository(remoteDataSource, databaseService)

    @Provides
    @Singleton
    fun provideAuthRepository(remoteDataSource: RemoteDataSource, databaseService: DatabaseService, sessionManager: SessionManager) = AuthRepository(remoteDataSource, databaseService, sessionManager)

    @Provides
    @Singleton
    fun provideUserRepository(remoteDataSource: RemoteDataSource, databaseService: DatabaseService) = UserRepository(remoteDataSource, databaseService)
}