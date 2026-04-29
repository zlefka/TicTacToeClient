package com.example.tictactoemobile.presentation.model

import com.example.tictactoemobile.presentation.viewmodel.GameStatusUi

data class GameViewData(
    val id: String,
    val board: BoardViewData,
    val player1Login: String,
    val player2Login: String?,
    val currentTurnId: String?,
    val isTwoPlayers: Boolean,
    val status: String,
    val winnerId: String?,
    val statusUi: GameStatusUi,
    val canMove: Boolean
)

data class BoardViewData(
    val cells: List<List<Int>>
)