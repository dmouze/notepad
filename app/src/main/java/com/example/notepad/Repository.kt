package com.example.notepad


import android.content.Context
import com.example.notepad.data.AppDb
import com.example.notepad.data.Note
import com.example.notepad.data.NoteDAO
import com.example.notepad.data.User
import com.example.notepad.data.UserDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class Repository(context: Context): UserDAO,NoteDAO {

    private val noteDao = AppDb.getInstance(context).noteDao()
    private val userDao = AppDb.getInstance(context).userDao()
    override suspend fun insertAll(notes: List<Note>) = withContext(Dispatchers.IO) {
        noteDao.insertAll(notes)
    }

    override suspend fun delete(notes: Note) = withContext(Dispatchers.IO) {
        noteDao.delete(notes)
    }

    override suspend fun update(notes: Note) = withContext(Dispatchers.IO) {
        noteDao.update(notes)
    }

    override suspend fun getNoteById(id: Int): Note? {
       return noteDao.getNoteById(id)
    }

    override suspend fun insert(user: User) = withContext(Dispatchers.IO) {
        userDao.insert(user)
    }

    override suspend fun delete(user: List<User>) = withContext(Dispatchers.IO) {
        userDao.delete(user)
    }

    override suspend fun update(user: User) = withContext(Dispatchers.IO) {
        userDao.update(user)
    }

    override suspend fun getUserByLogin(loginValue: String): User? {
        return userDao.getUserByLogin(loginValue)
    }

    override suspend fun getUserByLoginAndPassword(loginValue: String, passwordValue: String): User? {
        return userDao.getUserByLoginAndPassword(loginValue, passwordValue)
    }

    override fun getAllUsers(): Flow<List<User>> {
        return userDao.getAllUsers()
    }

    override fun getAllNotes(): Flow<List<Note>> {
        return noteDao.getAllNotes()
    }

    override suspend fun deleteAllUsers() = withContext(Dispatchers.IO) {
        userDao.deleteAllUsers()
    }

    override suspend fun deleteAllNotes() = withContext(Dispatchers.IO) {
        noteDao.deleteAllNotes()
    }


}