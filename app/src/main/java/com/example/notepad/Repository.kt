package com.example.notepad


import android.content.Context
import com.example.notepad.data.AppDatabase
import com.example.notepad.data.Note
import com.example.notepad.data.User
import kotlinx.coroutines.flow.Flow

class Repository(context: Context) {

    private val noteDao = AppDatabase.getInstance(context).noteDao()
    private val userDao = AppDatabase.getInstance(context).userDao()

    suspend fun insertUser(user: User) {
        userDao.insert(user)
    }

    suspend fun getUserById(id: Int) {
        userDao.getUserById(id)
    }

    fun getAllUsers(): Flow<List<User>> {
        return userDao.getAll()
    }

    suspend fun insertNotes(notes: List<Note>) {
        noteDao.insertAll(notes)
    }

    suspend fun getNoteById(id: Int) {
        noteDao.getNoteById(id)
    }

    fun getAllNotes(): Flow<List<Note>> {
        return noteDao.getAll()
    }

    suspend fun deleteNotes(notes: List<Note>) {
        noteDao.delete(notes)
    }

    suspend fun updateNotes(notes: Note) {
        noteDao.update(notes)
    }

    suspend fun deleteAllNotes() {
        noteDao.dropDatabase()
    }

    suspend fun deleteAllUsers() {
        userDao.dropDatabase()
    }

}