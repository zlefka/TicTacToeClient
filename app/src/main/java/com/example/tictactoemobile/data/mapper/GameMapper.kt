package com.example.tictactoemobile.data.mapper

import com.example.tic_tac_toe_mobile.data.dto.GameDto
import com.example.tic_tac_toe_mobile.data.entity.GameEntity
import com.example.tictactoemobile.domain.model.Game


fun GameDto.toDomain(): Game {
    return Game(
        id = id,
        board = board.cells,
        player1Id = player1,
        player2Id = player2.takeIf { it.isNotBlank() },
        currentTurnId = currentTurn,
        isTwoPlayers = isTwoPlayers,
        status = status,
        winnerId = winnerIs
    )
}

fun Game.toDto(): GameDto {
    return GameDto(
        id = id,
        board = com.example.tic_tac_toe_mobile.data.dto.BoardDto(cells = board),
        player1 = player1Id,
        player2 = player2Id.orEmpty(),
        currentTurn = currentTurnId,
        isTwoPlayers = isTwoPlayers,
        status = status,
        winnerIs = winnerId
    )
}

fun GameEntity.toDomain(): Game {
    return Game(
        id = serverGameId ?: gameId.toString(),
        board = stringToBoard(this.boardState)    ,
        player1Id = player1,
        player2Id = player2.takeIf { it.isNotBlank() },
        currentTurnId = currentTurn,
        isTwoPlayers = player2.isNotBlank(),
        status = status,
        winnerId = winner
    )
}

fun Game.toEntity(
    localId: Long = 0,
    createdAt: Long = System.currentTimeMillis(),
    finishedAt: Long? = null
): GameEntity {
    return GameEntity(
        gameId = localId,
        serverGameId = id,
        player1 = player1Id,
        player2 = player2Id.orEmpty(),
        boardState = boardToString(this.board) ,
        currentTurn = currentTurnId,
        winner = winnerId,
        status = status,
        createdAt = createdAt,
        finishedAt = finishedAt
    )
}

