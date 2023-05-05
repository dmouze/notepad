package com.example.notepad

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.notepad.data.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch


class MainViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = Repository(app.applicationContext)
    private var isPassInv = true
    private var isUserNew = true
    private var done = false

    fun done(): Boolean {
        return done
    }

    fun isPassInv(): Boolean {
        return isPassInv
    }

    fun isUserNew(): Boolean {
        return isUserNew
    }

    fun getAllUsers(): Flow<List<User>> {
        return repo.getAllUsers().flowOn(Dispatchers.IO)
    }

    private suspend fun getUserByLogin(loginValue: String): User? {
        return repo.getUserByLogin(loginValue)
    }

    fun registerUser(
        nameValue: String,
        loginValue: String,
        passwordValue: String,
        confirmPasswordValue: String
    ) {

        done = false
        viewModelScope.launch(Dispatchers.IO) {


            if (passwordValue != confirmPasswordValue) {
                isPassInv = true
            } else {

                isPassInv = false

                val existingUser = getUserByLogin(loginValue)

                if (existingUser != null) {
                    isUserNew = false
                }

                if (existingUser == null){
                    isUserNew = true
                }

                if (isUserNew && !isPassInv){
                    val user = User(
                        nameValue = nameValue,
                        loginValue = loginValue,
                        passwordValue = passwordValue
                    )
                    repo.insert(user)
                    done = true
                    isPassInv = false
                }
            }
        }
    }
}


