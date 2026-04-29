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
    val logoutCompleted: LiveData<Unit> get() = _logoutCompleted

    private val _joinCompleted = MutableLiveData<String?>()
    val joinCompleted: LiveData<String?> get() = _joinCompleted

    fun getGames() {
        viewModelScope.launch {
            try {
                val gamesRepository = repository.getAvailableGames()
                val currentUserId = repository.getCurrentUserId()

                val filteredGames = gamesRepository.filter { game ->
                    game.status == "WAITING" &&
                            game.player2Id == null &&
                            game.player1Id != currentUserId
                }

                val items = filteredGames.map { game ->
                    val creatorLogin = repository.getUserLoginById(game.player1Id) ?: game.player1Id
                    ItemViewData(id = game.id, login = creatorLogin)
                }

                _games.value = items
            } catch (e: Exception) {
                _error.value = e.message ?: "Unknown error"
            }
        }
    }

    fun joinGame(gameId: String) {
        viewModelScope.launch {
            try {
                val join = repository.joinGame(gameId)
                if (join != null) {
                    _joinCompleted.value = join.id
                } else {
                    _error.value = "Can't join the game"
                }
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

    fun onJoinNavigated() {
        _joinCompleted.value = null
    }

}