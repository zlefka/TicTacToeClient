package com.example.tictactoemobile.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tictactoemobile.data.repository.AuthRepository
import com.example.tictactoemobile.data.repository.GameRepository

class ExistingGamesFragmentViewModelFactory(private val repository: GameRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ExistingGamesFragmentViewModel::class.java)) {
            return ExistingGamesFragmentViewModel(repository) as T
        }
        throw java.lang.IllegalArgumentException("Unknown ViewModel")
    }
}