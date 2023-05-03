package com.example.notepad

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.notepad.data.AppDatabase
import com.example.notepad.data.Note
import com.example.notepad.data.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val noteDao = AppDatabase.getInstance(application).noteDao()
    private val userDao = AppDatabase.getInstance(application).userDao()

    fun getNotes(): Flow<List<Note>> {
        return noteDao.getAll()
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            AppDatabase.getInstance(application).clearAllTables()
            populateDatabase()
        }
    }

    private fun populateDatabase(){
        repeat(100){
            val time = System.currentTimeMillis()
            val note = Note(noteName = "${time % 100}", noteBody = "${time % 100}")
            CoroutineScope(viewModelScope.coroutineContext).launch {
                noteDao.insertAll(listOf(note))
            }

        }
    }
}