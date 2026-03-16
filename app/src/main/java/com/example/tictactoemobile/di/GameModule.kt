package com.example.tic_tac_toe_mobile.di

import com.example.tic_tac_toe_mobile.data.api.GameApi
import com.example.tic_tac_toe_mobile.data.api.RetrofitController
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class GameApiModule {
    @Provides
    fun provideGameApi(): GameApi {
        val provider = {Pair(null, null)}
        return RetrofitController.createApi(provider)
    }
}