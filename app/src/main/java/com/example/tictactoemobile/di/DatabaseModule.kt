package com.example.tictactoemobile.di

import android.content.Context
import androidx.room.Room
import com.example.tic_tac_toe_mobile.data.db.AppDatabase
import com.example.tic_tac_toe_mobile.data.db.CurrentUserDao
import com.example.tic_tac_toe_mobile.data.db.GameDao
import com.example.tic_tac_toe_mobile.data.db.UserDao
import com.example.tic_tac_toe_mobile.data.db.service.DatabaseService
import com.example.tictactoemobile.data.api.SessionManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "tic_tac_toe_db").fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideGameDao(database: AppDatabase): GameDao {
        return database.getGameDao()
    }

    @Provides
    @Singleton
    fun provideUserDao(database: AppDatabase): UserDao {
        return database.getUserDao()
    }

    @Provides
    @Singleton
    fun provideCurrentUserDao(database: AppDatabase): CurrentUserDao {
        return database.getCurrentUserDao()
    }

    @Provides
    @Singleton
    fun provideDatabaseService(
        gameDao: GameDao,
        userDao: UserDao,
        currentUserDao: CurrentUserDao
    ): DatabaseService = DatabaseService(gameDao, userDao, currentUserDao)

}

@Module
class AppModule(private val context: Context) {
    @Provides
    @Singleton
    fun provideContext(): Context = context

    @Provides
    @Singleton
    fun provideSessionManager(): SessionManager {
        return SessionManager()
    }
}