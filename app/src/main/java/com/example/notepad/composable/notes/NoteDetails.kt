package com.example.notepad.composable.notes

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.notepad.Constants
import com.example.notepad.Constants.noteDetailPlaceHolder
import com.example.notepad.R
import com.example.notepad.ui.theme.NotepadTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NoteDetails(
    noteId: Int,
    navController: NavController,
    viewModel: NotesViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val scope = rememberCoroutineScope()

    val note = remember {
        mutableStateOf(noteDetailPlaceHolder)
    }

    LaunchedEffect(true) {
        scope.launch(Dispatchers.IO) {
            note.value = viewModel.getNote(noteId) ?: noteDetailPlaceHolder
        }
    }

    NotepadTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
            Scaffold(
                topBar = {
                    AppBar(
                        title = note.value.title,
                        onIconClick = {
                            navController.navigate(Constants.noteEditNavigation(note.value.id))
                        },
                        icon = {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.edit_note),
                                contentDescription = "Edit note",
                                tint = Color.Black,
                            )
                        },
                        iconState = remember { mutableStateOf(true) }
                    )
                },

                ) {
                Column(
                    Modifier
                        .fillMaxSize()
                ) {

                    Text(
                        text = note.value.title,
                        modifier = Modifier.padding(top = 24.dp, start = 12.dp, end = 24.dp),
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = note.value.dateUpdated, Modifier.padding(12.dp), color = Color.Gray)
                    Text(text = note.value.note, Modifier.padding(12.dp), fontSize = 20.sp)
                }

            }
        }
    }
}