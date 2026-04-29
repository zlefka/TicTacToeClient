package com.example.tictactoemobile.presentation.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorPalette = lightColors(
    primary = Elements,
    primaryVariant = Background,
    secondary = Elements,
    background = Background,
    surface = Milk,
    error = Color.Red,

    onPrimary = White,
    onSecondary = White,
    onBackground = White,
    onSurface = Background
)

@Composable
fun TicTacToeTheme(
    content: @Composable () -> Unit
) {
    val extraColors = ExtraColors(
        currentGameInfoBackground = CurrentGameInfoBackground,
        gray = Gray
    )

    MaterialTheme(
        colors = LightColorPalette
    ) {
        androidx.compose.runtime.CompositionLocalProvider(
            LocalExtraColors provides extraColors,
            content = content
        )
    }
}

