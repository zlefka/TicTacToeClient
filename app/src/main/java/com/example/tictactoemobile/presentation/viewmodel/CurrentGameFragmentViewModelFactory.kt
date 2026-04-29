package com.example.tictactoemobile.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tictactoemobile.data.repository.GameRepository

class CurrentGameFragmentViewModelFactory(private val repository: GameRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CurrentGameFragmentViewModel::class.java)) {
            return CurrentGameFragmentViewModel(repository) as T
        }
        throw java.lang.IllegalArgumentException("Unknown ViewModel")
    }
}