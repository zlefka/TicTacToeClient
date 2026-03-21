package com.example.tictactoemobile.data.repository

import com.example.tic_tac_toe_mobile.data.db.service.DatabaseService
import com.example.tic_tac_toe_mobile.data.dto.LoginRequestDto
import com.example.tic_tac_toe_mobile.data.dto.SignUpRequestDto
import com.example.tic_tac_toe_mobile.data.entity.CurrentUserEntity
import com.example.tic_tac_toe_mobile.data.entity.UserEntity
import com.example.tic_tac_toe_mobile.data.repository.RemoteDataSource
import com.example.tictactoemobile.domain.model.User

class AuthRepository(private val remoteDataSource: RemoteDataSource,
                     private val databaseService: DatabaseService
) {
    suspend fun login(login: String, password: String): User?{
        val request = LoginRequestDto(login, password)
        val response = remoteDataSource.login(request) ?: return null
        val user = User(response.id, login)
        val userEntity = UserEntity(serverId = response.id, login = login, password = password)
        databaseService.insertUser(userEntity)
        val currentUserEntity = CurrentUserEntity(userId = response.id)
        databaseService.insertOrReplace(currentUserEntity)
        return user
    }

    suspend fun signUp(login: String, password: String): User? {
        val request = SignUpRequestDto(login, password)
        val response = remoteDataSource.signUp(request) ?: return null
        return if(response.success) login(login, password) else null
    }
}