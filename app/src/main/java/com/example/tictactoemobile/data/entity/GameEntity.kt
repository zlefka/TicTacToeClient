package com.example.tic_tac_toe_mobile.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Index

@Entity(tableName = "game", foreignKeys = [ForeignKey(entity = UserEntity::class, parentColumns = ["id"], childColumns = ["player1"], onDelete = ForeignKey.CASCADE),
    ForeignKey(entity = UserEntity::class, parentColumns = ["id"], childColumns = ["player2"], onDelete = ForeignKey.CASCADE)],
    indices = [Index("player1"), Index("player2")])
data class GameEntity(
    @PrimaryKey(autoGenerate = true) val gameId: Long = 0,
    @ColumnInfo(name = "server_game_id") val serverGameId: String? = null,
    @ColumnInfo(name = "player1") val player1: String,
    @ColumnInfo(name = "player2") val player2: String,
    @ColumnInfo(name = "board") val boardState: String,
    @ColumnInfo(name = "current_turn") val currentTurn: String,
    @ColumnInfo val winner: String?,
    @ColumnInfo val status: String,
    @ColumnInfo(name = "created_at") val createdAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "finished_at") val finishedAt: Long? = null
)
