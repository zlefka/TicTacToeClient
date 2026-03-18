package com.example.tictactoemobile.data.mapper

import com.example.tic_tac_toe_mobile.data.dto.MoveDto
import com.example.tictactoemobile.domain.model.Move

fun MoveDto.toDomain(): Move = Move(row = row, col = col)

fun Move.toDto(): MoveDto = MoveDto(row = row, col = col)

