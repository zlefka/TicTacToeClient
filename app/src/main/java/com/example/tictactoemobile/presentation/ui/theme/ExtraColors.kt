package com.example.tictactoemobile.presentation.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class ExtraColors(
    val currentGameInfoBackground: Color,
    val gray: Color
)

val LocalExtraColors = staticCompositionLocalOf {
    ExtraColors(
        currentGameInfoBackground = Color.Unspecified,
        gray = Color.Unspecified
    )
}