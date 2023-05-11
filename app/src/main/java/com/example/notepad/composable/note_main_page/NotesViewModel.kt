package com.example.notepad.composable.note_main_page

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.notepad.data.Note
import com.example.notepad.data.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class NotesViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = Repository(app.applicationContext)

    fun getAllNotes(): Flow<List<Note>> {
        return repo.getAllNotes().flowOn(Dispatchers.IO)
    }

    suspend fun insertAllNotes(notes: List<Note>) = withContext(Dispatchers.IO) {
        repo.insertAll(notes)
    }

    suspend fun deleteNotes(notes: Note) = withContext(Dispatchers.IO) {
        repo.delete(notes)
    }

    suspend fun updateNotes(notes: Note) = withContext(Dispatchers.IO) {
        repo.update(notes)
    }

    suspend fun getNoteById(id: Int): Note? {
        return repo.getNoteById(id)
    }


}

