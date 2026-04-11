package com.example.tictactoemobile.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tictactoemobile.data.repository.AuthRepository
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
                if (exists) {
                    Log.i("Exists", "Login Exists")
                    _state.value = StatesOfSignup.LoginAlreadyExists
                }
                else signup(login, password)
            }

        }
    }

    suspend fun signup(login: String, password: String) {
        _state.value = StatesOfSignup.Loading
        try {
            val success = repository.signUp(login, password)
            if (success) {
                Log.i("Exists", "User is NOT null")
                _state.value = StatesOfSignup.Success
            } else {
                Log.i("Exists", "User is null")
                _state.value = StatesOfSignup.LoginAlreadyExists
            }
        } catch (e: Exception) {
            _state.value = StatesOfSignup.Error(e.message ?: "Unknown error")
        }
    }
}