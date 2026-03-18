package com.example.tictactoemobile.domain.model

data class Game(
    val id: String,
    val board: List<List<Int>>,
    val player1Id: String,
    val player2Id: String?,
    val currentTurnId: String,
    val isTwoPlayers: Boolean,
    val status: String,
    val winnerId: String?
)