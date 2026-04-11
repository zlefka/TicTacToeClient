package com.example.tictactoemobile.data.repository

import android.util.Log
import com.example.tic_tac_toe_mobile.data.db.service.DatabaseService
import com.example.tic_tac_toe_mobile.data.dto.LoginRequestDto
import com.example.tic_tac_toe_mobile.data.dto.SignUpRequestDto
import com.example.tic_tac_toe_mobile.data.entity.CurrentUserEntity
import com.example.tic_tac_toe_mobile.data.entity.UserEntity
import com.example.tic_tac_toe_mobile.data.repository.RemoteDataSource
import com.example.tictactoemobile.data.api.SessionManager
import com.example.tictactoemobile.domain.model.User

class AuthRepository(
    private val remoteDataSource: RemoteDataSource,
    private val databaseService: DatabaseService,
    private val sessionManager: SessionManager
) {
    suspend fun login(login: String, password: String): User? {

        val request = LoginRequestDto(login, password)
        val response = remoteDataSource.login(request) ?: return null

        val currentUser = databaseService.getCurrentUser()
        val existingUser = databaseService.getUserByServerId(response.id)

        val userEntity = if (existingUser != null) {
            existingUser
        } else {
            val newUser = UserEntity(
                serverId = response.id,
                login = login,
                password = password
            )
            databaseService.insertUser(newUser)
            newUser
        }

        if (currentUser != null && currentUser.userId != userEntity.serverId) {
            databaseService.clearAll()
        }

        val currentUserEntity = CurrentUserEntity(userId = userEntity.serverId)
        databaseService.insertOrReplace(currentUserEntity)

        sessionManager.login = login
        sessionManager.password = password

        return User(userEntity.serverId, userEntity.login)

    }

    suspend fun signUp(login: String, password: String): Boolean {
        return try {
            val request = SignUpRequestDto(login, password)
            val response = remoteDataSource.signUp(request)
            Log.i("retrofit", "${response.toString()}")
            true
        } catch (e: retrofit2.HttpException) {
            if (e.code() == 409) false
            else throw e
        }
    }

    suspend fun isLoginAlreadyExists(login: String): Boolean {
        return databaseService.isLoginExists(login)
    }
}