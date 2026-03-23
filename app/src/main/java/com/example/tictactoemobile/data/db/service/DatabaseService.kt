package com.example.tic_tac_toe_mobile.data.db.service

import com.example.tic_tac_toe_mobile.data.db.CurrentUserDao
import com.example.tic_tac_toe_mobile.data.db.GameDao
import com.example.tic_tac_toe_mobile.data.db.UserDao
import com.example.tic_tac_toe_mobile.data.entity.CurrentUserEntity
import com.example.tic_tac_toe_mobile.data.entity.GameEntity
import com.example.tic_tac_toe_mobile.data.entity.UserEntity
import com.example.tictactoemobile.domain.model.CurrentUser
import javax.inject.Inject

class DatabaseService @Inject constructor(
    private val gameDao: GameDao,
    private val userDao: UserDao,
    private val currentUserDao: CurrentUserDao
) {
    suspend fun saveGame(game: GameEntity) {
        gameDao.insert(game)
    }

    suspend fun getGame(id: Long) {
        gameDao.getGameById(id)
    }

    suspend fun getGameByServerId(serverId: String): GameEntity? {
        return gameDao.getGameByServerId(serverId)
    }

    suspend fun getGamesForUser(userId: String) {
        gameDao.getGamesForUser(userId)
    }

    suspend fun getGamesByStatus(status: String): List<GameEntity> {
        return gameDao.getGamesByStatus(status)
    }

    suspend fun deleteGame(game: GameEntity) {
        gameDao.delete(game)
    }

    suspend fun updateGame(game: GameEntity) {
        gameDao.update(game)
    }

    suspend fun insertUser(user: UserEntity): Long {
        return userDao.insert(user)
    }

    suspend fun updateUser(user: UserEntity) {
        userDao.update(user)
    }

    suspend fun deleteUser(user: UserEntity) {
        userDao.delete(user)
    }

    suspend fun getUserByLogin(login: String): UserEntity? {
        return userDao.getUserByLogin(login)
    }

    suspend fun getUserById(userId: String): UserEntity? {
        return userDao.getUserById(userId)
    }

    suspend fun getAllUsers(): List<UserEntity> {
        return userDao.getAllUsers()
    }

    suspend fun isLoginExists(login: String): Boolean {
        return userDao.isLoginExists(login)
    }

    suspend fun insertOrReplace(currentUser: CurrentUserEntity) {
        currentUserDao.insertOrReplace(currentUser)
    }

    suspend fun getCurrentUser(): CurrentUserEntity? {
        return currentUserDao.getCurrentUser()
    }

    suspend fun delete(currentUser: CurrentUserEntity) {
        currentUserDao.delete(currentUser)
    }

    suspend fun updateEnterTime(time: Long = System.currentTimeMillis()) {
        currentUserDao.updateEnterTime(time)
    }

    suspend fun clearAll() {
        gameDao.clear()
        userDao.clear()
        currentUserDao.clear()
    }

}