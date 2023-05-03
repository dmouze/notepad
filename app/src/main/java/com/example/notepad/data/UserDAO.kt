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
    suspend fun insert(user: User)

    @Insert
    suspend fun insertAll(user: List<User>)

    @Delete
    suspend fun delete(user: List<User>)

    @Update
    suspend fun update(user: User)

    @Query("SELECT * FROM users_table")
    fun getAll(): Flow<List<User>>

    @Query("DELETE FROM users_table")
    suspend fun dropDatabase()
}