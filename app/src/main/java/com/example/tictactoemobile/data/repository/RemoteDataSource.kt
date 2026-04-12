package com.example.tic_tac_toe_mobile.data.repository

import android.util.Log
import com.example.tic_tac_toe_mobile.data.api.GameApi
import com.example.tic_tac_toe_mobile.data.dto.*
import com.example.tictactoemobile.data.dto.GameRequestDto
import javax.inject.Inject

private const val TAG = "GameService"

class RemoteDataSource @Inject constructor (private val gameApi: GameApi) {

    suspend fun createGame(request: GameRequestDto): GameDto? {
        return try {
            Log.i("create game", "remote data source request sent")

            gameApi.newGame(request)
        } catch (e: java.lang.Exception) {
            Log.e(TAG, "Can't create new game", e)
            null
        }
    }

    suspend fun signUp(requestDto: SignUpRequestDto): SignUpResponseDto? {
        return gameApi.registerUser(requestDto)
    }

    suspend fun login(request: LoginRequestDto): LoginResponseDto? {
        return try {
            gameApi.loginUser(request)
        } catch (e: java.lang.Exception) {
            Log.e(TAG, "Login error", e)
            null
        }
    }

    suspend fun getGame(id: String): GameDto? {
        return try {
            gameApi.getGameById(id)
        } catch (e: java.lang.Exception) {
            Log.e(TAG, "Can't get the game", e)
            null
        }
    }

    suspend fun availableGames(): List<GameDto>? {
        return try {
            gameApi.availableGames()
        } catch (e: java.lang.Exception) {
            Log.e(TAG, "Can't get available games", e)
            null
        }
    }

    suspend fun getUser(id: String): UserDto? {
        return try {
            gameApi.getUserById(id)
        } catch (e: java.lang.Exception) {
            Log.e(TAG, "Can't get this user", e)
            null
        }
    }

    suspend fun joinGame(id: String): GameDto? {
        return try {
            gameApi.joinGame(id)
        } catch (e: java.lang.Exception) {
            Log.e(TAG, "Can't join the game", e)
            null
        }
    }

    suspend fun makeMove(id: String, move: MoveDto): GameDto? {
        return try {
            gameApi.makeMove(id, move)
        } catch (e: java.lang.Exception) {
            Log.e(TAG, "Can't make move", e)
            null
        }
    }
}