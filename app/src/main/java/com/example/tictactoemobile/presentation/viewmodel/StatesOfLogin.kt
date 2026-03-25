package com.example.tictactoemobile.presentation.viewmodel

import com.example.tictactoemobile.presentation.model.UserViewData

sealed class StatesOfLogin {
    data class Success(val user: UserViewData) : StatesOfLogin()
    object LoginError: StatesOfLogin()
    object PasswordError: StatesOfLogin()
    object Loading: StatesOfLogin()
    data class Error(val reason: String) : StatesOfLogin()
}