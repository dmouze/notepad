package com.example.notepad.composable.notes

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.notepad.data.notes_data.Note
import com.example.notepad.data.notes_data.NoteDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesViewModel(
    private val repo: NoteDAO,
) : ViewModel() {

    val getNotes: LiveData<List<Note>> = repo.getNotes()


    fun deleteNotes(note: Note) {
        viewModelScope.launch(Dispatchers.IO){
            repo.deleteNote(note)
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO){
            repo.updateNote(note)
        }
    }

    fun createNote(title: String, note: String) {
        viewModelScope.launch(Dispatchers.IO){
            repo.insertNote(Note(title = title, note = note))
        }
    }

    suspend fun getNote(noteId : Int) : Note? {
        return repo.getNoteById(noteId)
    }

}

@Suppress("UNCHECKED_CAST")
class NotesViewModelFactory(
    private val repo: NoteDAO,
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return  NotesViewModel(
            repo = repo,
        ) as T
    }

}