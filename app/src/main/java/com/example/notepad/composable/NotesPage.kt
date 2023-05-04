package com.example.notepad.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.notepad.NotesViewModel


@Composable
fun NotesPage(navController: NavController, notesViewModel: NotesViewModel = viewModel()) {
    val notes = notesViewModel.getNotes().collectAsState(emptyList())

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(top = 50.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Text(text = "Witaj",)
        }
    }
}
