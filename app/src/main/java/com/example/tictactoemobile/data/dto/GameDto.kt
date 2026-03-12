package com.example.tic_tac_toe_mobile.data.dto

data class GameDto(
    val id: String,
    val board: BoardDto,
    val player1: String,
    val player2: String,
    val currentTurn: String,
    val isTwoPlayers: Boolean,
    val status: String,
    val winnerIs: String?

)

data class BoardDto(
    val cells: List<List<Int>>
)