package com.example.tictactoemobile.presentation.mapper

import com.example.tictactoemobile.domain.model.Game
import com.example.tictactoemobile.domain.model.User
import com.example.tictactoemobile.presentation.model.BoardViewData
import com.example.tictactoemobile.presentation.model.GameViewData
import com.example.tictactoemobile.presentation.model.ItemViewData
import com.example.tictactoemobile.presentation.model.UserViewData

fun User.toItemViewData(): UserViewData {
    return UserViewData(
        id = this.id,
        login = this.login
    )
}

fun Game.toItemViewData(): ItemViewData {
    return ItemViewData(
        id = this.id,
        login = this.player1Id
    )
}

fun Game.toViewData(): GameViewData {
    return GameViewData(
        id = this.id,
        board = BoardViewData(
            cells = this.board
        ),
        player1 = this.player1Id,
        player2 = this.player2Id,
        currentTurn = currentTurnId,
        isTwoPlayers = this.isTwoPlayers,
        status = this.status,
        winnerIs = this.winnerId
    )
}