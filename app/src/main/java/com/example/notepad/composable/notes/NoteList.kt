package com.example.notepad.composable.notes

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.notepad.AppBar
import com.example.notepad.Constants
import com.example.notepad.Constants.orPlaceHolderList
import com.example.notepad.R
import com.example.notepad.data.notes_data.Note
import com.example.notepad.data.notes_data.getDay
import com.example.notepad.ui.theme.NotepadTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnusedMaterialScaffoldPaddingParameter",
    "StateFlowValueCalledInComposition"
)
@Composable
fun NoteList(
    navController: NavController,
    notesViewModel: NotesViewModel,
    userId : Int
) {

    val deleteText = remember {
        mutableStateOf("")
    }

    val noteQuery = remember {
        mutableStateOf("")
    }

    val notesDelete = remember {
        mutableStateOf(listOf<Note>())
    }

    notesViewModel.userId = remember {
        mutableStateOf(userId)
    }

    val openDialog = remember { mutableStateOf(false) }

    val notes = notesViewModel.notes

    val context = LocalContext.current

    println(userId)

    NotepadTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
            Scaffold(
                topBar = {
                    AppBar(
                        title = stringResource(R.string.notepad_),
                        onIconClick = {
                            if (notes.value.isNotEmpty()) {
                                openDialog.value = true
                                deleteText.value = "Are you sure you want to delete all notes ?"
                                notesDelete.value = notes.value
                            } else {
                                Toast.makeText(context, "No Notes found.", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = ImageVector.vectorResource(
                                    id = R.drawable.note_delete
                                ),
                                contentDescription = stringResource(id = R.string.delete_note),
                                tint = Color.Black
                            )
                        },
                        iconState = remember { mutableStateOf(true) }

                    )
                },
                floatingActionButton = {
                    NotesFab(
                        contentDescription = stringResource(R.string.create_note),
                        action = { navController.navigate("createnote_page") },
                        icon = R.drawable.note_add_icon
                    )
                }

            ) {
                Column {
                    SearchBar(noteQuery)
                    NotesList(
                        notes = notes.value.orPlaceHolderList(),
                        query = noteQuery,
                        openDialog = openDialog,
                        deleteText = deleteText,
                        navController = navController,
                        notesToDelete = notesDelete
                    )
                }

                DeleteDialog(
                    openDialog = openDialog,
                    text = deleteText,
                    notesToDelete = notesDelete,
                    action = {
                        notesDelete.value.forEach {
                            notesViewModel.deleteNotes(it)
                        }
                    })
            }

        }
    }
}

@Composable
fun SearchBar(query: MutableState<String>) {
    Column(Modifier.padding(top = 12.dp, start = 12.dp, end = 12.dp, bottom = 12.dp)) {
        TextField(
            value = query.value,
            placeholder = { Text("Search note...") },
            maxLines = 1,
            onValueChange = { query.value = it },
            modifier = Modifier
                .background(Color.White)
                .clip(RoundedCornerShape(12.dp))
                .fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Black,
            ),
            trailingIcon = {
                AnimatedVisibility(
                    visible = query.value.isNotEmpty(),
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    IconButton(onClick = { query.value = "" }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.plus),
                            contentDescription = stringResource(
                                R.string.search
                            )
                        )
                    }
                }

            })

    }
}

@Composable
fun NotesList(
    notes: List<Note>,
    openDialog: MutableState<Boolean>,
    query: MutableState<String>,
    deleteText: MutableState<String>,
    navController: NavController,
    notesToDelete: MutableState<List<Note>>,
) {
    var previousHeader = ""
    LazyColumn(
        contentPadding = PaddingValues(12.dp),
        modifier = Modifier.background(Color.White)
    ) {
        val queriedNotes = if (query.value.isEmpty()) {
            notes
        } else {
            notes.filter { it.note.contains(query.value) || it.title.contains(query.value) }
        }
        itemsIndexed(queriedNotes) { _, note ->
            if (note.getDay() != previousHeader) {
                Column(
                    modifier = Modifier
                        .padding(6.dp)
                        .fillMaxWidth()
                ) {
                    Text(text = note.getDay(), color = Color.Black)
                }
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                )
                previousHeader = note.getDay()
            }


            NoteListItem(
                note,
                openDialog,
                deleteText = deleteText,
                navController,
                notesToDelete = notesToDelete,
                noteBackGround = Color.Cyan
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp)
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteListItem(
    note: Note,
    openDialog: MutableState<Boolean>,
    deleteText: MutableState<String>,
    navController: NavController,
    noteBackGround: Color,
    notesToDelete: MutableState<List<Note>>
) {

    return Box(
        modifier = Modifier
            .height(120.dp)
            .clip(RoundedCornerShape(12.dp))
    ) {
        Column(
            modifier = Modifier
                .background(noteBackGround)
                .fillMaxWidth()
                .height(120.dp)
                .combinedClickable(interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(bounded = false),
                    onClick = {
                        if (note.id != 0) {
                            navController.navigate(Constants.noteDetailNavigation(note.id))
                        }
                    },
                    onLongClick = {
                        if (note.id != 0) {
                            openDialog.value = true
                            println(note.id)
                            deleteText.value = "Are you sure you want to delete this note ?"
                            notesToDelete.value = mutableListOf(note)
                        }
                    }
                )

        ) {
            Row {
                Column {
                    Text(
                        text = note.title,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                    Text(
                        text = note.note,
                        color = Color.Black,
                        maxLines = 3,
                        modifier = Modifier.padding(12.dp)
                    )
                    Text(
                        text = note.dateUpdated,
                        color = Color.Black,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                }
            }

        }
    }
}

@Composable
fun NotesFab(contentDescription: String, icon: Int, action: () -> Unit) {
    return FloatingActionButton(
        onClick = { action.invoke() },
        backgroundColor = MaterialTheme.colors.primary
    ) {
        Icon(
            ImageVector.vectorResource(id = icon),
            contentDescription = contentDescription,
            tint = Color.Black
        )

    }
}

@Composable
fun DeleteDialog(
    openDialog: MutableState<Boolean>,
    text: MutableState<String>,
    action: () -> Unit,
    notesToDelete: MutableState<List<Note>>
) {
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                Text(text = "Delete Note")
            },
            text = {
                Column {
                    Text(text.value)
                }
            },
            buttons = {
                Row(
                    modifier = Modifier.padding(all = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Column {
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.Black,
                                contentColor = Color.White
                            ),
                            onClick = {
                                action.invoke()
                                openDialog.value = false
                                notesToDelete.value = mutableListOf()
                            }
                        ) {
                            Text("Yes")
                        }
                        Spacer(modifier = Modifier.padding(12.dp))
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.Black,
                                contentColor = Color.White
                            ),
                            onClick = {
                                openDialog.value = false
                                notesToDelete.value = mutableListOf()
                            }
                        ) {
                            Text("No")
                        }
                    }

                }
            }
        )
    }
}