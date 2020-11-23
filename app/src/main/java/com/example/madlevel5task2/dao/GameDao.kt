package com.example.madlevel5task2.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.madlevel5task2.model.Game

@Dao
interface GameDao {

    @Query("SELECT * FROM gameTable")
    fun getAllGames(): LiveData<List<Game>>

    @Insert
    fun addGame(backlogGame: Game)

    @Delete
    fun deleteGame(backlogGame: Game)

    @Query("DELETE FROM gameTable")
    suspend fun deleteAllGames()
}