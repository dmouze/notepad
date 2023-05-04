package com.example.notepad

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.example.notepad.data.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext


class UsersViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = Repository(app.applicationContext)

    fun getUsers(): Flow<List<User>> {
        return repo.getAllUsers()
    }

    init {

    }

    suspend fun registerUser(context: Context) {
        User = new User()
        }
    }


}