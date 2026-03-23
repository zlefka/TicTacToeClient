package com.example.tictactoemobile.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tictactoemobile.data.repository.AuthRepository
import kotlinx.coroutines.launch

class LoginFragmentViewModel(private val repository: AuthRepository): ViewModel() {
    private val _state = MutableLiveData<StatesOfLogin>()
    val state: LiveData<StatesOfLogin> get() = _state

    fun validate(login: String, password: String) {
        if(login.isBlank()) _state.value = StatesOfLogin.LoginError
        else if(password.isBlank()) _state.value = StatesOfLogin.PasswordError
        else {
            login(login, password)
        }
    }

    fun login(login: String, password: String) {
        _state.value = StatesOfLogin.Loading
        viewModelScope.launch() {
            try {
                if (repository.login(login, password) != null) {
                    _state.value = StatesOfLogin.Success
                } else _state.value = StatesOfLogin.Error("User is not found")
            } catch (e: Exception) {
                e.message ?: "Unknown error"
            }
        }
    }
}