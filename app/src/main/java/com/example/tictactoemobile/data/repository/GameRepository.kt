package com.example.tictactoemobile.data.repository

import android.util.Log
import com.example.tic_tac_toe_mobile.data.db.service.DatabaseService
import com.example.tic_tac_toe_mobile.data.entity.UserEntity
import com.example.tic_tac_toe_mobile.data.repository.RemoteDataSource
import com.example.tictactoemobile.data.dto.GameRequestDto
import com.example.tictactoemobile.data.mapper.toDomain
import com.example.tictactoemobile.data.mapper.toDto
import com.example.tictactoemobile.data.mapper.toEntity
import com.example.tictactoemobile.domain.model.Game
import com.example.tictactoemobile.domain.model.Move

class GameRepository(
    private val remoteDataSource: RemoteDataSource,
    private val databaseService: DatabaseService
) {
    suspend fun getGame(id: String): Game {
        return try {
            val gameDto = remoteDataSource.getGame(id)
            val game = gameDto.toDomain()
            syncUser(game.player1Id)
            if(game.player2Id != null) {
                syncUser(game.player2Id)
            }
            saveOrUpdateGame(game)
            Log.d("REPO", "game = $game")
            game
        } catch (e: Exception) {
            val gameEntity = databaseService.getGameByServerId(id)
            Log.d("REPO", "game = $gameEntity")
            if (gameEntity != null) {
                gameEntity.toDomain()
            } else {
                Log.e("error", "Can't get the game", e)
                throw e
            }
        }
    }

    private suspend fun saveOrUpdateGame(game: Game) {
        val existing = databaseService.getGameByServerId(game.id)
        val entity = if(existing != null) {
            game.toEntity(localId = existing.gameId, createdAt = existing.createdAt, finishedAt = existing.finishedAt)
        } else {
            game.toEntity()
        }
        if(existing != null) {
            databaseService.updateGame(entity)
        } else databaseService.saveGame(entity)
    }

    suspend fun createGame(playerSymbol: String, isBot: Boolean): Game? {
        val request = GameRequestDto(playerSymbol, isBot)
        Log.i("create game", "request created")

        val newGameDto = remoteDataSource.createGame(request) ?: return null
        Log.i("GAME_DEBUG", "DTO from server: $newGameDto")

        val newGame = newGameDto.toDomain()
        Log.i("GAME_DEBUG", "p1=${newGame.player1Id}, p2=${newGame.player2Id}, winner=${newGame.winnerId}")
        syncUser(newGame.player1Id)
        if(newGame.player2Id != null) {
            syncUser(newGame.player2Id)
        }
        Log.i("create game", "users checked")
        val gameEntity = newGame.toEntity()
        Log.i(
            "GAME_DEBUG",
            "ENTITY GAME: player1=${gameEntity.player1}, player2=${gameEntity.player2}, winner=${gameEntity.winner}"
        )
        databaseService.saveGame(gameEntity)

        return newGame
    }

    private suspend fun syncUser(serverId: String) {
        val localUser = databaseService.getUserByServerId(serverId)
        if(localUser != null && localUser.login != localUser.serverId) return
        val remoteUserDto = remoteDataSource.getUser(serverId)

        if(remoteUserDto != null) {
            val remoteUser = remoteUserDto.toDomain()
            val entity = remoteUser.toEntity(password = localUser?.password ?: "")
            if(localUser == null) {
                databaseService.insertUser(entity)
            } else if (localUser.login == localUser.serverId) {
                databaseService.updateUser(entity)
            }
            return
        }
        if(localUser == null) {
            databaseService.insertUser(
                UserEntity(
                    serverId = serverId,
                    login = serverId,
                    password = ""
                )
            )
        }
    }


    suspend fun joinGame(id: String): Game? {
        val join = remoteDataSource.joinGame(id) ?: return null

        val gameDomain = join.toDomain()
        val gameEntity = gameDomain.toEntity()

        syncUser(gameEntity.player1)
        if (gameEntity.player2 != null) {
            syncUser(gameEntity.player2)
        }

        saveOrUpdateGame(gameDomain)
        return gameDomain
    }

    suspend fun getAvailableGames(): List<Game> {
        val games = remoteDataSource.availableGames()
        return if (games != null) {
            val domainGames = games.map { it.toDomain() }
            val gameEntities = domainGames.map { it.toEntity() }
            gameEntities.forEach {
                syncUser(it.player1)
                if(it.player2 != null) {
                    syncUser(it.player2)
                }
                saveOrUpdateGame(it.toDomain())
            }
            domainGames
        } else {
            val gamesList = databaseService.getGamesByStatus("WAITING")
            gamesList.map { it.toDomain() }
        }
    }

    suspend fun getCurrentUserId(): String? {
        return databaseService.getCurrentUser()?.userId
    }

    suspend fun getUserLoginById(userId: String): String? {
        return databaseService.getUserByServerId(userId)?.login
    }

    suspend fun makeMove(id: String, move: Move): Game? {
        val moveDto = move.toDto()
        val updatedGame = remoteDataSource.makeMove(id, moveDto) ?: return null
        val gameDomain = updatedGame.toDomain()
        val gameEntity = gameDomain.toEntity()
        syncUser(gameEntity.player1)
        if(gameEntity.player2 != null) {
            syncUser(gameEntity.player2)
        }
        saveOrUpdateGame(gameDomain)
        return gameDomain
    }

    suspend fun clearDatabase() {
        databaseService.clearAll()
    }
}