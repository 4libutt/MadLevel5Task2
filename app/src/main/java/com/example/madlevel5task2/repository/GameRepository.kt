package com.example.madlevel5task2.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.madlevel5task2.dao.GameDao
import com.example.madlevel5task2.database.GamesDatabase
import com.example.madlevel5task2.model.Game


class GameRepository(context: Context) {

    private val gameDao: GameDao

    init {
        val database = GamesDatabase.getDatabase(context)
        gameDao = database!!.backlogGameDao()
    }

    fun getAllBacklogGames(): LiveData<List<Game>> {
        return gameDao.getAllGames()
    }

    fun addGame(game: Game) {
        gameDao.addGame(game)
    }

    suspend fun deleteAllGames() {
        gameDao.deleteAllGames()
    }

    fun deleteGame(game: Game) {
        gameDao.deleteGame(game)
    }
}