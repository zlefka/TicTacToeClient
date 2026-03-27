package com.example.tictactoemobile.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tictactoemobile.data.repository.AuthRepository

class SignupFragmentViewModelFactory(private val repository: AuthRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SignupFragmentViewModel::class.java)) {
            return SignupFragmentViewModel(repository) as T
        }
        throw java.lang.IllegalArgumentException("Unknown ViewModel")
    }
}