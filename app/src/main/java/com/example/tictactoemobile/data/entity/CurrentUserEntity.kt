package com.example.tic_tac_toe_mobile.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "current_user", foreignKeys = [ ForeignKey(entity = UserEntity::class, parentColumns = ["server_id"], childColumns = ["user_id"], onDelete = ForeignKey.CASCADE)], indices = [Index("user_id")])
data class CurrentUserEntity(
    @PrimaryKey val id: Int = 1,
    @ColumnInfo(name = "user_id") val userId: String,
    @ColumnInfo(name = "last_login") val lastLogin: Long = System.currentTimeMillis()
)
