package com.example.tictactoemobile.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tictactoemobile.data.repository.AuthRepository

class LoginFragmentViewModelFactory(private val repository: AuthRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(LoginFragmentViewModel::class.java)) {
            return LoginFragmentViewModel(repository) as T
        }
        throw java.lang.IllegalArgumentException("Unknown ViewModel")
    }
}