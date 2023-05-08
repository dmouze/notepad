package com.example.notepad

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.notepad.data.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = Repository(app.applicationContext)
    private var userExist = false
    private var done = false

    fun getAllUsers(): Flow<List<User>> {
        return repo.getAllUsers().flowOn(Dispatchers.IO)
    }


    suspend fun checkIfUserExists(loginValue: String): Boolean {
        return withContext(Dispatchers.IO) {
            val user = repo.getUserByLogin(loginValue)
            user != null
        }
    }


    fun registerUser(
        nameValue: String,
        loginValue: String,
        passwordValue: String,
        confirmPasswordValue: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            done = false
            if (passwordValue == confirmPasswordValue) {
                userExist = false
                val user = User(
                    nameValue = nameValue,
                    loginValue = loginValue,
                    passwordValue = passwordValue
                )
                repo.insert(user)
                done = true
            } else {
                userExist = true
            }
        }
    }
}

