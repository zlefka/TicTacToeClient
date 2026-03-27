package com.example.tic_tac_toe_mobile.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tic_tac_toe_mobile.data.entity.*

@Database(
    version = 2,
    entities = [
        CurrentUserEntity::class,
        GameEntity::class,
        UserEntity::class
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getGameDao(): GameDao
    abstract fun getUserDao(): UserDao
    abstract fun getCurrentUserDao(): CurrentUserDao

}