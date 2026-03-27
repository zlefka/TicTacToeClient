package com.example.tictactoemobile.presentation.viewmodel

sealed class StatesOfSignup {
    object Success: StatesOfSignup()
    object LoginError: StatesOfSignup()
    object LoginAlreadyExists: StatesOfSignup()
    object PasswordError: StatesOfSignup()
    object ConfirmPasswordError: StatesOfSignup()
    object Loading: StatesOfSignup()
    data class Error(val reason: String) : StatesOfSignup()
}