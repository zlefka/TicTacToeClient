package com.example.tictactoemobile.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tictactoemobile.data.repository.GameRepository
import com.example.tictactoemobile.presentation.mapper.toItemViewData
import com.example.tictactoemobile.presentation.model.ItemViewData
import kotlinx.coroutines.launch

class ExistingGamesFragmentViewModel(private val repository: GameRepository) : ViewModel() {
    private val _games = MutableLiveData<List<ItemViewData>>()
    val games: LiveData<List<ItemViewData>> get() = _games

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _logoutCompleted = MutableLiveData<Unit>()
    val logoutCompleted: LiveData<Unit> = _logoutCompleted

    fun getGames(){
        viewModelScope.launch {
            try {
                val gamesRepository = repository.getAvailableGames()
                _games.value = gamesRepository.map {it.toItemViewData()}
            } catch (e: Exception) {
                _error.value = e.message ?: "Unknown error"
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            repository.clearDatabase()
            _logoutCompleted.postValue(Unit)
        }
    }

}