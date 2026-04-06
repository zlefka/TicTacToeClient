package com.example.tictactoemobile.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tictactoemobile.data.repository.GameRepository

class CreateGameFragmentViewModelFactory(private val repository: GameRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CreateGameFragmentViewModel::class.java)) {
            return CreateGameFragmentViewModel(repository) as T
        }
        throw java.lang.IllegalArgumentException("Unknown ViewModel")
    }
}