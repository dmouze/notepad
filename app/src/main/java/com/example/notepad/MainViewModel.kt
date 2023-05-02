package com.example.notepad

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.notepad.data.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


class MainViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = Repository(app.applicationContext)

    fun getNotes(): Flow<List<Note>> {
        return repo.getAll()
    }

    init {
        CoroutineScope(Dispatchers.IO).launch {
            repo.dropDatabase()
            populateDatabase()
        }
    }

    private fun populateDatabase(){
        repeat(100){
            val time = System.currentTimeMillis()
            val note = Note(noteName = "${time % 100}", noteBody = "${time % 100}")
            CoroutineScope(viewModelScope.coroutineContext).launch {
                repo.insertAll(listOf(note))
            }

        }
    }
}

