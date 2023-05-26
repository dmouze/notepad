package com.example.notepad.composable.notes

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.notepad.Constants
import com.example.notepad.Constants.orPlaceHolderList
import com.example.notepad.R
import com.example.notepad.composable.login_register.UserViewModel
import com.example.notepad.data.notes_data.Note
import com.example.notepad.data.notes_data.getDay
import com.example.notepad.ui.theme.NotepadTheme
import com.example.notepad.ui.theme.PurpleGrey80
import com.example.notepad.ui.theme.textWhiteColor

@SuppressLint(
    "UnusedMaterial3ScaffoldPaddingParameter", "UnusedMaterialScaffoldPaddingParameter",
    "StateFlowValueCalledInComposition"
)
@Composable
fun NoteList(
    navController: NavController,
    notesViewModel: NotesViewModel,
    userId: Int
) {

    val userViewModel: UserViewModel = viewModel()
    val backPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    val dialogOpen = remember { mutableStateOf(false) }

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

    val username = notesViewModel.getUserName(userId)

    val context = LocalContext.current


    DisposableEffect(key1 = backPressedDispatcher) {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                dialogOpen.value = true
            }
        }

        backPressedDispatcher?.addCallback(callback)

        onDispose {
            callback.remove()
        }
    }

    if (dialogOpen.value) {
        LogoutDialog(
            onConfirmLogout = {
                userViewModel.logout()
                navController.navigate("login_page")
                dialogOpen.value = false
            },
            onDismiss = {
                dialogOpen.value = false
            }
        )
    }

    NotepadTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
            Scaffold(
                topBar = {
                    AppBar(
                        title = "Welcome in _notepad $username!",
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
                textColor = textWhiteColor,
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
                        .padding(10.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = note.getDay(),
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp
                    )
                }
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                )
                previousHeader = note.getDay()
            }


            NoteListItem(
                note,
                openDialog,
                deleteText = deleteText,
                navController,
                notesToDelete = notesToDelete,
                noteBackGround = PurpleGrey80
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
            Column(
                modifier = Modifier.weight(1f)
            ){
                Spacer(modifier = Modifier.height(10.dp))
            Text(
                    text = note.title,
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 2.dp)
                )
                Text(
                    text = note.note,
                    color = Color.Black,
                    fontSize = 16.sp,
                    maxLines = 2,
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.BottomEnd)
            ) {
                Text(
                    text = note.dateUpdated,
                    fontSize = 14.sp,
                    color = Color.DarkGray,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp),
                )
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

@Composable
fun LogoutDialog(
    onConfirmLogout: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Logout", fontWeight = FontWeight.Bold)
        },
        text = {
            Text("Are you sure you want to logout?", fontWeight = FontWeight.SemiBold)
        },
        confirmButton = {
            Button(colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black),
                onClick = onConfirmLogout
            ) {
                Text("Logout", color = Color.White)
            }
        },
        dismissButton = {
            Button(colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black),
                onClick = onDismiss
            ) {
                Text("Cancel", color = Color.White)
            }
        }
    )
}




