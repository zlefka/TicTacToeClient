package com.example.tictactoemobile.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tictactoemobile.data.repository.GameRepository
import com.example.tictactoemobile.presentation.mapper.toViewData
import com.example.tictactoemobile.presentation.model.ItemViewData
import kotlinx.coroutines.launch

class ExistingGamesFragmentViewModel(private val repository: GameRepository) : ViewModel() {
    private val _games = MutableLiveData<List<ItemViewData>>()
    val games: LiveData<List<ItemViewData>> get() = _games

    fun getGames(){
        viewModelScope.launch {
            try {
                val gamesRepository = repository.getAvailableGames()
                _games.value = gamesRepository.map {it.toViewData()}
            } catch (e: Exception) {
                e.message ?: "Unknown error"
            }
        }
    }
}