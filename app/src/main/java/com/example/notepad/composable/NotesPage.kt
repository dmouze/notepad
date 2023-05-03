package com.example.notepad.composable

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.notepad.MainViewModel
import com.example.notepad.data.Note

@Composable
fun NotesPage(navController: NavController, mainViewModel: MainViewModel = viewModel()) {
    val notes = mainViewModel.getNotes().collectAsState(emptyList())

    LazyColumn {
        items(notes.value) { note: Note ->
            Text(text = "${note.noteName}: ${note.noteBody}")
        }
    }
}