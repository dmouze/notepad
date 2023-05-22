package com.example.notepad.composable.notes

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.notepad.data.notes_data.Note
import com.example.notepad.data.notes_data.NotesRepository
import com.example.notepad.data.user_data.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class NotesViewModel(
    private val repo: NotesRepository, private val userRepo: UserRepository
) : ViewModel() {

    var userId = mutableStateOf(0)

    val getNotesByUserId: List<Note> = runBlocking {
        repo.getNotesByUserId(userId.value)
    }


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

    fun createNote(title: String, note: String, userId: Int?) {
        viewModelScope.launch(Dispatchers.IO){
            repo.insertNote(Note(title = title, note = note, userId = userId))
        }
    }

    suspend fun getNote(noteId : Int) : Note? {
        return repo.getNoteById(noteId)
    }

}

@Suppress("UNCHECKED_CAST")
class NotesViewModelFactory(
    private val repo: NotesRepository, private val userRepo: UserRepository,
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return  NotesViewModel(
            repo = repo, userRepo = userRepo,
        ) as T
    }

}