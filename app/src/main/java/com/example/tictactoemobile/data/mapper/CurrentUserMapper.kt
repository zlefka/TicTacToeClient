package com.example.tictactoemobile.data.mapper

import com.example.tic_tac_toe_mobile.data.entity.CurrentUserEntity
import com.example.tictactoemobile.domain.model.CurrentUser

fun CurrentUserEntity.toDomain(): CurrentUser =
    CurrentUser(userId = userId, lastLogin = lastLogin)

fun CurrentUser.toEntity(): CurrentUserEntity =
    CurrentUserEntity(userId = userId, lastLogin = lastLogin)

