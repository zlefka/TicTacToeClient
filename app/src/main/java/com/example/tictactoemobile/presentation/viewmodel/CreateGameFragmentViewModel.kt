package com.example.tictactoemobile.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tictactoemobile.data.repository.GameRepository
import com.example.tictactoemobile.presentation.mapper.toViewData
import kotlinx.coroutines.launch

class CreateGameFragmentViewModel(private val repository: GameRepository): ViewModel() {

    private val _state = MutableLiveData<StatesOfCreatingGames>()
    val state: LiveData<StatesOfCreatingGames> get() = _state

    fun createGame(playerSymbol: String, isBot: Boolean) {
        _state.value = StatesOfCreatingGames.Loading
        viewModelScope.launch {
            try {
                val game = repository.createGame(playerSymbol, isBot)
                Log.i("create game", "request was sent")
                if(game != null) {
                    Log.i("create game", "the game isn't null")
                    val viewData = game.toViewData()
                    _state.value = StatesOfCreatingGames.Success(viewData)
                } else _state.value = StatesOfCreatingGames.Error("Can't create game")
            } catch (e: Exception) {
                _state.value = StatesOfCreatingGames.Error(e.message ?: "Unknown error")
            }
        }
    }
}