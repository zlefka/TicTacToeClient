package com.example.tictactoemobile.presentation.viewmodel


sealed class GameStatusUi {
    object WaitingPlayers : GameStatusUi()
    object Victory : GameStatusUi()
    object Defeat : GameStatusUi()
    object Draw : GameStatusUi()
    object YourTurn : GameStatusUi()
    data class OpponentTurn(val opponentLogin: String) : GameStatusUi()
}
