package com.example.tictactoemobile.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tictactoemobile.data.repository.GameRepository
import com.example.tictactoemobile.domain.model.Game
import com.example.tictactoemobile.domain.model.Move
import com.example.tictactoemobile.presentation.model.BoardViewData
import com.example.tictactoemobile.presentation.model.GameViewData
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class CurrentGameFragmentViewModel(private val repository: GameRepository) : ViewModel() {
    private var pollingJob: Job? = null
    private val _state = MutableStateFlow(StatesOfCurrentGame())
    val state: StateFlow<StatesOfCurrentGame> = _state
    private var currentGameId: String? = null

    fun loadGame(gameId: String) {
        currentGameId = gameId
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            try {
                val game = repository.getGame(gameId)
                Log.d("VM", "loadGame called: $gameId ${game.id}")
                _state.value = _state.value.copy(
                    game = mapGameToViewData(game),
                    isLoading = false,
                    error = null
                )
                startPolling(gameId)
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    error = e.message ?: "Unknown error",
                    isLoading = false
                )
            }
        }
    }

    fun makeMove(row: Int, col: Int) {
        val gameId = currentGameId ?: return

        viewModelScope.launch {
            _state.value = _state.value.copy(error = null)
            try {
                val updatedGame = repository.makeMove(gameId, Move(row, col))
                if (updatedGame != null) {
                    _state.value = _state.value.copy(
                        game = mapGameToViewData(updatedGame),
                        error = null
                    )
                } else {
                    _state.value = _state.value.copy(
                        error = "Can't make move"
                    )
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    error = e.message ?: "Unknown error"
                )
            }
        }
    }

    private fun startPolling(gameId: String) {
        pollingJob?.cancel()
        pollingJob = viewModelScope.launch {
            while (isActive) {
                try {
                    val game = repository.getGame(gameId)
                    _state.value = _state.value.copy(
                        game = mapGameToViewData(game),
                        error = null
                    )
                } catch (e: Exception) {
                    _state.value = _state.value.copy(
                        error = e.message ?: "Unknown error"
                    )
                }
                delay(1000)
            }
        }
    }

    override fun onCleared() {
        pollingJob?.cancel()
        super.onCleared()
    }

    private suspend fun mapGameToViewData(game: Game): GameViewData {
        val currentUserId = repository.getCurrentUserId()
        val player1Login = repository.getUserLoginById(game.player1Id) ?: game.player1Id
        val player2Login = game.player2Id?.let { repository.getUserLoginById(it) ?: it }

        val opponentLogin =
            if (currentUserId == game.player1Id) player2Login ?: "Opponent" else player1Login

        val statusState = when {
            game.player2Id == null -> GameStatusUi.WaitingPlayers
            game.winnerId != null -> if (game.winnerId == currentUserId) GameStatusUi.Victory else GameStatusUi.Defeat
            game.status == "DRAW" -> GameStatusUi.Draw
            game.currentTurnId == currentUserId -> GameStatusUi.YourTurn
            else -> GameStatusUi.OpponentTurn(opponentLogin)
        }

        val canMove = game.player2Id != null && game.winnerId == null && game.status != "DRAW"
                && game.currentTurnId == currentUserId

        return GameViewData(
            game.id, BoardViewData(game.board), player1Login, player2Login,
            game.currentTurnId, game.isTwoPlayers, game.status, game.winnerId, statusState, canMove
        )
    }
}
