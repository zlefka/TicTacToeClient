package com.example.tictactoemobile.presentation.model

class GameViewData(
    val id: String,
    val board: BoardViewData,
    val player1: String,
    val player2: String?,
    val currentTurn: String?,
    val isTwoPlayers: Boolean,
    val status: String,
    val winnerIs: String?
)

data class BoardViewData(
    val cells: List<List<Int>>
)