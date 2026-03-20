package com.example.tic_tac_toe_mobile.data.db

import androidx.room.*
import com.example.tic_tac_toe_mobile.data.entity.GameEntity

@Dao
interface GameDao {
    @Insert
    suspend fun insert(game:GameEntity): Long

    @Update
    suspend fun update(game: GameEntity)

    @Delete
    suspend fun delete(game: GameEntity)

    @Query("SELECT * FROM game WHERE gameId = :gameId")
    suspend fun getGameById(gameId: Long): GameEntity?
    @Query("SELECT * FROM game WHERE server_game_id = :serverGameId")
    suspend fun getGameByServerId(serverGameId: String): GameEntity?


    @Query("SELECT * FROM game WHERE player1 = :userId OR player2 = :userId ORDER BY created_at DESC")
    suspend fun getGamesForUser(userId: String): List<GameEntity>

    @Query("SELECT * FROM game WHERE status = :status")
    suspend fun getGamesByStatus(status: String): List<GameEntity>
}