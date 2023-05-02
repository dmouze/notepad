package com.example.notepad.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDAO {

    @Insert
    suspend fun insertAll(notes: List<User>)

    @Delete
    suspend fun delete(notes: List<User>)

    @Update
    suspend fun update(users: User)

    @Query("SELECT * FROM users_table")
    fun getAll(): Flow<List<User>>

    @Query("DELETE FROM users_table")
    suspend fun dropDatabase()
}