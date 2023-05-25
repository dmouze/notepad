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

class NotesViewModel(
    private val repo: NotesRepository, private val userRepo: UserRepository
) : ViewModel() {

    var userId = mutableStateOf(0)
    val notes = mutableStateOf<List<Note>>(emptyList())
    var username = ""

    init {
        viewModelScope.launch {
            notes.value = repo.getNotesByUserId(userId.intValue)
            println("init $userId")
        }
    }

    fun getUserName(userId: Int): String {
        viewModelScope.launch {
            username = userRepo.getUserName(userId)
        }
        return username
    }

    private suspend fun getNotesByUserId(userId: Int): List<Note> {
        return repo.getNotesByUserId(userId)
    }

    fun reloadNotes(userId: Int) {
        viewModelScope.launch {
            notes.value = repo.getNotesByUserId(userId)
            println("reload $userId")
        }
    }

    fun deleteNotes(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteNote(note)
            notes.value = getNotesByUserId(userId.intValue)
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.updateNote(note)
        }
    }

    fun createNote(title: String, note: String, userId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val newNote = Note(title = title, note = note, userId = userId)
            repo.insertNote(newNote)
            notes.value = getNotesByUserId(userId = userId)
        }
    }

    suspend fun getNote(noteId: Int): Note? {
        return repo.getNoteById(noteId)
    }

}

@Suppress("UNCHECKED_CAST")
class NotesViewModelFactory(
    private val repo: NotesRepository, private val userRepo: UserRepository,
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NotesViewModel(
            repo = repo, userRepo = userRepo,
        ) as T
    }

}