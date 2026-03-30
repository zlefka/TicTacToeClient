package com.example.tic_tac_toe_mobile.di

import com.example.tic_tac_toe_mobile.data.api.GameApi
import com.example.tic_tac_toe_mobile.data.api.RetrofitController
import com.example.tic_tac_toe_mobile.data.repository.RemoteDataSource
import com.example.tictactoemobile.data.api.SessionManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class GameApiModule {
    @Provides
    fun provideGameApi(sessionManager: SessionManager): GameApi {
        val provider = {
            Pair(sessionManager.login, sessionManager.password)
        }
        return RetrofitController.createApi(provider)
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(gameApi: GameApi): RemoteDataSource = RemoteDataSource(gameApi)
}