package com.example.tic_tac_toe_mobile.data.db

import androidx.room.*
import com.example.tic_tac_toe_mobile.data.entity.CurrentUserEntity

@Dao
interface CurrentUserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(currentUser: CurrentUserEntity)

    @Query("SELECT * FROM current_user LIMIT 1")
    suspend fun getCurrentUser(): CurrentUserEntity?

    @Delete
    suspend fun delete(currentUser: CurrentUserEntity)

    @Query("UPDATE current_user SET last_login = :time WHERE id = 1")
    suspend fun updateEnterTime(time: Long = System.currentTimeMillis())
}