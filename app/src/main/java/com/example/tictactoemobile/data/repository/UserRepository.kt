package com.example.tictactoemobile.data.repository

import com.example.tic_tac_toe_mobile.data.db.service.DatabaseService
import com.example.tic_tac_toe_mobile.data.repository.RemoteDataSource
import com.example.tictactoemobile.data.mapper.toDomain
import com.example.tictactoemobile.data.mapper.toEntity
import com.example.tictactoemobile.domain.model.User

class UserRepository (private val remoteDataSource: RemoteDataSource,
                      private val databaseService: DatabaseService
){

    suspend fun getUserById(id: String): User? {
        val localUser = databaseService.getUserByServerId(id)
        if (localUser != null && localUser.login != localUser.serverId) return localUser.toDomain()

        val remoteUserDto = remoteDataSource.getUser(id) ?: return null
        val userDomain = remoteUserDto.toDomain()
        val userEntity = userDomain.toEntity(password = localUser?.password ?: "")
        if (localUser == null) {
            databaseService.insertUser(userEntity)
        } else {
            databaseService.updateUser(userEntity)
        }
        return userDomain
    }

    suspend fun getAllUsers(): List<User> {
        val allUsers = databaseService.getAllUsers()
        return allUsers.map { it.toDomain() }
    }
}
