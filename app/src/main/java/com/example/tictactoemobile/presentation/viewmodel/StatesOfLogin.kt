package com.example.tictactoemobile.presentation.viewmodel

sealed class StatesOfLogin {
    object Success : StatesOfLogin()
    object LoginError: StatesOfLogin()
    object PasswordError: StatesOfLogin()
    object Loading: StatesOfLogin()
    data class Error(val reason: String) : StatesOfLogin()
}