package com.example.tictactoemobile.presentation.viewmodel

import com.example.tictactoemobile.presentation.model.GameViewData

sealed class StatesOfCreatingGames {
    data class Success(val game: GameViewData) : StatesOfCreatingGames()
    data class Error(val reason: String) : StatesOfCreatingGames()
    object Loading: StatesOfCreatingGames()
}