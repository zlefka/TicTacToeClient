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
import com.example.tictactoemobile.domain.model.User

class GameRepository(
    private val remoteDataSource: RemoteDataSource,
    private val databaseService: DatabaseService
) {
    suspend fun getGame(id: String): Game? {
        val gameDto = remoteDataSource.getGame(id)
        if (gameDto != null) {
            val game = gameDto.toDomain()
            val gameEntity = game.toEntity()
            databaseService.saveGame(gameEntity)
            return game
        } else {
            val gameEntityDB = databaseService.getGameByServerId(id)
            val gameDB = gameEntityDB?.toDomain()
            return gameDB
        }
    }

    suspend fun createGame(playerSymbol: String, isBot: Boolean): Game? {
        // Создаём DTO для запроса
        val request = GameRequestDto(playerSymbol, isBot)
        Log.i("create game", "request created")

        // Запрос на сервер
        val newGameDto = remoteDataSource.createGame(request) ?: return null
        Log.i("GAME_DEBUG", "DTO from server: $newGameDto")

        // Преобразуем в доменную модель
        val newGame = newGameDto.toDomain()
        Log.i("GAME_DEBUG", "p1=${newGame.player1Id}, p2=${newGame.player2Id}, winner=${newGame.winnerId}")

        // Проверяем и вставляем пользователей, чтобы Foreign key не падал
        ensureUserExists(newGame.player1Id)
        newGame.player2Id?.let { ensureUserExists(it) } // если второй игрок есть
        Log.i("create game", "users checked")
        // Преобразуем в Entity и сохраняем в базу
        val gameEntity = newGame.toEntity()
        Log.i(
            "GAME_DEBUG",
            "ENTITY GAME: player1=${gameEntity.player1}, player2=${gameEntity.player2}, winner=${gameEntity.winner}"
        )
        databaseService.saveGame(gameEntity)

        return newGame
    }

    private suspend fun ensureUserExists(serverId: String) {
        databaseService.insertUser(
            UserEntity(
                serverId = serverId,
                login = serverId,
                password = ""
            )
        )
    }


    suspend fun joinGame(id: String): Game? {
        val join = remoteDataSource.joinGame(id)
        if (join != null) {
            val gameDomain = join.toDomain()
            val gameEntity = gameDomain.toEntity()
            databaseService.updateGame(gameEntity)
            return gameDomain
        } else {
            val gameEntityDB = databaseService.getGameByServerId(id) ?: return null
            return gameEntityDB.toDomain()
        }
    }

    suspend fun getAvailableGames(): List<Game> {
        val games = remoteDataSource.availableGames()
        return if (games != null) {
            val domainGames = games.map { it.toDomain() }
            val gameEntities = domainGames.map { it.toEntity() }
            gameEntities.forEach { databaseService.saveGame(it) }
            domainGames
        } else {
            val gamesList = databaseService.getGamesByStatus("WAITING")
            gamesList.map { it.toDomain() }
        }
    }

    suspend fun makeMove(id: String, move: Move): Game? {
        val moveDto = move.toDto()
        val updatedGame = remoteDataSource.makeMove(id, moveDto) ?: return null
        val gameDomain = updatedGame.toDomain()
        val gameEntity = gameDomain.toEntity()
        databaseService.updateGame(gameEntity)
        return gameDomain
    }

    suspend fun clearDatabase() {
        databaseService.clearAll()
    }
}