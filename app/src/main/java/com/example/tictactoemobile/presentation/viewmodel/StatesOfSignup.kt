package com.example.tictactoemobile.presentation.viewmodel

import com.example.tictactoemobile.presentation.model.UserViewData

sealed class StatesOfSignup {
    data class Success(val user: UserViewData) : StatesOfSignup()
    object LoginError: StatesOfSignup()
    object LoginAlreadyExists: StatesOfSignup()
    object PasswordError: StatesOfSignup()
    object ConfirmPasswordError: StatesOfSignup()
    object Loading: StatesOfSignup()
    data class Error(val reason: String) : StatesOfSignup()
}