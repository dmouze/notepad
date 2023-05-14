package com.example.notepad.composable.login_register

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.notepad.data.Repository
import com.example.notepad.data.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginRegisterViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = Repository(app.applicationContext)
    private var userExist = false
    private val currentUser = MutableStateFlow<User?>(null)
    val userFlow = currentUser.asStateFlow()

    suspend fun checkPassword(username: String, password: String): Boolean {
        return withContext(Dispatchers.IO) {
            val user = getUserByLoginAndPassword(username, password)
            user != null
        }
    }

    private fun setCurrentUser(user: User) {
        currentUser.value = user
    }

    fun getCurrentUser(): StateFlow<User?> {
        return currentUser
    }

    suspend fun checkIfUserExists(loginValue: String): Boolean {
        return withContext(Dispatchers.IO) {
            val user = repo.getUserByLogin(loginValue)
            user != null
        }
    }
    private suspend fun getUserByLoginAndPassword(
        loginValue: String,
        passwordValue: String
    ): User? {
        return repo.getUserByLoginAndPassword(loginValue, passwordValue)
    }

    suspend fun login(loginValue: String, passwordValue: String): Boolean {
        Log.d(
            "login",
            "login function called"
        )
        return withContext(Dispatchers.IO) {
            val user = getUserByLoginAndPassword(loginValue, passwordValue)
            if (user != null) {
                setCurrentUser(user)
                userFlow.apply {
                    currentUser.asStateFlow()
                }
                println(currentUser.value)
                Log.d(
                    "login",
                    "login function called working"
                )
                true
            } else {
                false
            }
        }
    }

    fun logout() {
        currentUser.value = null
    }


    fun registerUser(
        nameValue: String,
        loginValue: String,
        passwordValue: String,
        confirmPasswordValue: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            if (passwordValue == confirmPasswordValue) {
                userExist = false
                val user = User(
                    nameValue = nameValue,
                    loginValue = loginValue,
                    passwordValue = passwordValue
                )
                repo.insert(user)
            } else {
                userExist = true
            }
        }
    }
}
