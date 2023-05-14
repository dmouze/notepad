package com.example.notepad.composable.notes

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavController
import com.example.notepad.Constants
import com.example.notepad.Constants.noteDetailPlaceHolder
import com.example.notepad.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun NoteDetails(noteId: Int, navController: NavController, viewModel: NotesViewModel) {
    val scope = rememberCoroutineScope()

    val note = remember {
        mutableStateOf(noteDetailPlaceHolder)
    }

    LaunchedEffect(true) {
        scope.launch(Dispatchers.IO) {
            note.value = viewModel.getNote(noteId) ?: noteDetailPlaceHolder
        }
    }

}