package com.example.tic_tac_toe_mobile.data.dto

data class SignUpResponseDto(
    val message: String? = null,
    val error: String? = null
) {
    val success: Boolean
        get() = error == null && message?.contains("created") == true
}
