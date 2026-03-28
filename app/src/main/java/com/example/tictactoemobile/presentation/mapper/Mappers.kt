package com.example.tictactoemobile.presentation.mapper

import com.example.tictactoemobile.domain.model.Game
import com.example.tictactoemobile.domain.model.User
import com.example.tictactoemobile.presentation.model.ItemViewData
import com.example.tictactoemobile.presentation.model.UserViewData

fun User.toViewData(): UserViewData {
    return UserViewData(
        id = this.id,
        login = this.login
    )
}

fun Game.toViewData(): ItemViewData {
    return ItemViewData(
        id = this.id,
        login = this.player1Id
    )
}