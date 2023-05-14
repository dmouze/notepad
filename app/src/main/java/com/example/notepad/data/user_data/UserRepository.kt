package com.example.notepad.data.user_data

import android.content.Context
import com.example.notepad.data.AppDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class UserRepository(context: Context): UserDAO{
    private val userDao = AppDb.getInstance(context).userDao()

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


    override suspend fun deleteAllUsers() = withContext(Dispatchers.IO) {
        userDao.deleteAllUsers()
    }
}