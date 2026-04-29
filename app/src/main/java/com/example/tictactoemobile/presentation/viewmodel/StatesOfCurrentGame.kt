package com.example.tictactoemobile.presentation.viewmodel

import com.example.tictactoemobile.presentation.model.GameViewData

data class StatesOfCurrentGame(
    val game: GameViewData? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)