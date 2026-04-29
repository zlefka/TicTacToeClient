package com.example.tictactoemobile.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.tic_tac_toe_mobile.R
import com.example.tictactoemobile.presentation.model.GameViewData
import com.example.tictactoemobile.presentation.ui.theme.LocalExtraColors
import com.example.tictactoemobile.presentation.ui.theme.TicTacToeTheme
import com.example.tictactoemobile.presentation.viewmodel.*

class CurrentGameFragment : Fragment() {
    private lateinit var viewModel: CurrentGameFragmentViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val repository = (activity as MainActivity).appComponent.getGameRepository()
        val factory = CurrentGameFragmentViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(CurrentGameFragmentViewModel::class.java)
        Log.d("DEBUG", "CurrentGameFragment opened")
        return ComposeView(requireContext()).apply {
            setContent {
                TicTacToeTheme {
                    val state by viewModel.state.collectAsState()
                    CurrentGameScreen(state = state, onCellClick = { row, col ->
                        viewModel.makeMove(row, col)
                    }, onBackClick = {
                        navigateToExistingGames()
                    })
                }

            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner
        ) {
            navigateToExistingGames()
        }

        val args = CurrentGameFragmentArgs.fromBundle(requireArguments())
        val gameId = args.gameId
        viewModel.loadGame(gameId)

    }


    override fun onDestroyView() {
        super.onDestroyView()
    }

    private fun navigateToExistingGames() {
        val navOptions =
            NavOptions.Builder().setPopUpTo(R.id.existingGamesFragment, false)
                .setLaunchSingleTop(true).build()
        val action =
            CurrentGameFragmentDirections.actionCurrentGameFragmentToExistingGamesFragment()
        findNavController().navigate(action, navOptions)
    }
}

@Composable
fun CurrentGameScreen(
    state: StatesOfCurrentGame,
    onCellClick: (Int, Int) -> Unit,
    onBackClick: () -> Unit
) {
    when {
        state.game != null -> GameContent(state.game, onCellClick, onBackClick)
        state.isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        state.error != null -> Text(text = state.error)
        else -> Text("EMPTY STATE")
    }
}

@Composable
fun GameContent(game: GameViewData, onCellClick: (Int, Int) -> Unit, onBackClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.Center),
            shape = RoundedCornerShape(16.dp),
            backgroundColor = MaterialTheme.colors.surface,
            elevation = 6.dp
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {

                Header(game, onBackClick)

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_cross),
                        contentDescription = "X", tint = MaterialTheme.colors.primary,
                        modifier = Modifier.size(30.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = stringResource(id = R.string.let_s_play), fontSize = 28.sp, fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.background, style = MaterialTheme.typography.h5
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.ic_circle),
                        contentDescription = "O", tint = MaterialTheme.colors.primary,
                        modifier = Modifier.size(30.dp)
                    )
                }


                Spacer(modifier = Modifier.height(24.dp))

                PlayersInfo(game)

                Spacer(modifier = Modifier.height(24.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f, fill = false),
                    contentAlignment = Alignment.Center
                ) {
                    GameBoard(game, onCellClick)
                }

                Spacer(modifier = Modifier.height(16.dp))

                GameFooter(game)
            }
        }
    }
}


@Composable
fun Header(game: GameViewData, onBackClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.back_to_games_arrow),
            fontSize = 16.sp,
            color = MaterialTheme.colors.primary,
            modifier = Modifier
                .weight(1f)
                .clickable { onBackClick() }
        )
        Text(
            text = game.status,
            color = MaterialTheme.colors.surface,
            fontSize = 16.sp,
            modifier = Modifier
                .background(
                    color = MaterialTheme.colors.primary, shape = RoundedCornerShape(20.dp)
                )
                .padding(horizontal = 10.dp, vertical = 4.dp)
        )
    }
}

@Composable
fun PlayersInfo(game: GameViewData) {
    val colors = LocalExtraColors.current
    Card(backgroundColor = colors.currentGameInfoBackground, shape = RoundedCornerShape(10.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .height(30.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(game.player1Login, fontSize = 16.sp)
            Text(
                text = "VS", fontSize = 14.sp, color = colors.gray,
                modifier = Modifier.weight(1f), textAlign = TextAlign.Center
            )
            Text(game.player2Login ?: "-", fontSize = 16.sp)
        }
    }

}

@Composable
fun GameBoard(game: GameViewData, onCellClick: (Int, Int) -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = when (game.statusUi) {
                GameStatusUi.WaitingPlayers -> stringResource(R.string.status_waiting_players)
                GameStatusUi.Victory -> stringResource(R.string.status_victory)
                GameStatusUi.Defeat -> stringResource(R.string.status_defeat)
                GameStatusUi.Draw -> stringResource(R.string.status_draw)
                GameStatusUi.YourTurn -> stringResource(R.string.status_your_turn)
                is GameStatusUi.OpponentTurn ->
                    stringResource(R.string.status_opponent_turn, game.statusUi.opponentLogin)
            },
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(12.dp))
        game.board.cells.forEachIndexed { rowIndex, row ->
            Row {
                row.forEachIndexed { colIndex, cell ->
                    Cell(cell, enabled = game.canMove && cell == 0) {
                        onCellClick(rowIndex, colIndex)
                    }
                }
            }
        }
    }
}

@Composable
fun Cell(value: Int, enabled: Boolean, onClick: () -> Unit) {
    val colors = LocalExtraColors.current
    Card(
        modifier = Modifier
            .size(90.dp)
            .padding(4.dp)
            .clickable(enabled = enabled) { onClick() },
        backgroundColor = colors.currentGameInfoBackground,
        shape = RoundedCornerShape(6.dp), elevation = 2.dp
    ) {
        Box(contentAlignment = Alignment.Center) {
            when (value) {
                1 -> Icon(
                    painter = painterResource(id = R.drawable.ic_cross), contentDescription = "X",
                    tint = MaterialTheme.colors.primary, modifier = Modifier.size(40.dp)
                )
                2 -> Icon(
                    painter = painterResource(id = R.drawable.ic_circle), contentDescription = "O",
                    tint = MaterialTheme.colors.primary, modifier = Modifier.size(40.dp)
                )
            }
        }
    }
}

@Composable
fun GameFooter(game: GameViewData) {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Text(text = "ID: ${game.id}", fontSize = 10.sp, color = Color.Gray)
    }
}

//@Preview
//@Composable
//fun PreviewApp() {
//    TicTacToeTheme {
//        CurrentGameScreen()
//    }
//}
