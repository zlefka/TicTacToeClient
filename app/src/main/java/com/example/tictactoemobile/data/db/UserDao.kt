package com.example.tic_tac_toe_mobile.data.db

import androidx.room.*
import com.example.tic_tac_toe_mobile.data.entity.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: UserEntity)

    @Update
    suspend fun update(user: UserEntity)

    @Delete
    suspend fun delete(user: UserEntity)

    @Query("DELETE FROM users")
    suspend fun clear()

    @Query("SELECT * FROM users WHERE login = :login")
    suspend fun getUserByLogin(login: String): UserEntity?

    @Query("SELECT * FROM users WHERE server_id = :userId")
    suspend fun getUserById(userId: String): UserEntity?
    @Query("SELECT * FROM users WHERE server_id = :userId")
    suspend fun getUserByServerId(userId: String): UserEntity?

    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<UserEntity>

    @Query("SELECT EXISTS(SELECT 1 FROM users WHERE login = :login)")
    suspend fun isLoginExists(login: String): Boolean
}