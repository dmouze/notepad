package com.example.notepad

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.notepad.data.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


class NotesViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = Repository(app.applicationContext)

    fun getNotes(): Flow<List<Note>> {
        return repo.getAllNotes()
    }

    init {
        populateDatabase()
    }

    private fun populateDatabase(){
        repeat(5){
            val time = System.currentTimeMillis()
            val note = Note(title = "$time", note = "$time")
            CoroutineScope(viewModelScope.coroutineContext).launch {
                repo.insertNotes(listOf(note))
            }

        }
    }
}