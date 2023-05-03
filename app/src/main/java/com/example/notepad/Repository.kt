package com.example.notepad


import com.example.notepad.data.Note
import com.example.notepad.data.NoteDAO
import com.example.notepad.data.User
import com.example.notepad.data.UserDAO
import kotlinx.coroutines.flow.Flow

class Repository(private val noteDao: NoteDAO, private val userDao: UserDAO) {

    suspend fun insertUser(user: User) {
        userDao.insert(user)
    }

    fun getAllUsers(): Flow<List<User>> {
        return userDao.getAll()
    }

    // Metody dla tabeli notatek
    suspend fun insertNotes(notes: List<Note>) {
        noteDao.insertAll(notes)
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

    suspend fun insertUsers(users: List<User>) {
        userDao.insertAll(users)
    }
}