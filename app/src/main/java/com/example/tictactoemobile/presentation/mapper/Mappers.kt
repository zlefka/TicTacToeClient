package com.example.tictactoemobile.presentation.mapper

import com.example.tictactoemobile.domain.model.Game
import com.example.tictactoemobile.domain.model.Move
import com.example.tictactoemobile.domain.model.User
import com.example.tictactoemobile.presentation.model.*
import com.example.tictactoemobile.presentation.viewmodel.GameStatusUi

fun User.toItemViewData(): UserViewData {
    return UserViewData(
        id = this.id,
        login = this.login
    )
}

fun MoveViewData.toMoveDomain(): Move {
    return Move(this.row, this.col)
}

fun Game.toViewData(): GameViewData {
    return GameViewData(
        id = this.id,
        board = BoardViewData(
            cells = this.board
        ),
        player1Login = this.player1Id,
        player2Login = this.player2Id,
        currentTurnId = currentTurnId,
        isTwoPlayers = this.isTwoPlayers,
        status = this.status,
        statusUi = GameStatusUi.WaitingPlayers,
        canMove = false,
        winnerId = this.winnerId
    )
}