package com.example.tictactoemobile.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tictactoemobile.data.repository.AuthRepository
import com.example.tictactoemobile.presentation.mapper.toViewData
import kotlinx.coroutines.launch

class SignupFragmentViewModel(private val repository: AuthRepository) : ViewModel() {
    private val _state = MutableLiveData<StatesOfSignup>()
    val state: LiveData<StatesOfSignup> get() = _state

    fun validate(login: String, password: String, confirmPassword: String) {
        viewModelScope.launch {
            if (login.isBlank()) _state.value = StatesOfSignup.LoginError
            else if (password.isBlank() || confirmPassword.isBlank()) _state.value =
                StatesOfSignup.PasswordError
            else if (confirmPassword != password) _state.value = StatesOfSignup.ConfirmPasswordError
            else {

                val exists = repository.isLoginAlreadyExists(login)
                if (exists) _state.value = StatesOfSignup.LoginAlreadyExists
                else signup(login, password)
            }

        }
    }

    suspend fun signup(login: String, password: String) {
        _state.value = StatesOfSignup.Loading
        try {
            val user = repository.signUp(login, password)
            if (user != null) {
                val viewData = user.toViewData()
                _state.value = StatesOfSignup.Success(viewData)
            } else _state.value = StatesOfSignup.LoginAlreadyExists
        } catch (e: Exception) {
            _state.value = StatesOfSignup.Error(e.message ?: "Unknown error")
        }
    }
}