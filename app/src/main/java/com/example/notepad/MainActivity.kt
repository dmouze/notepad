package com.example.notepad

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.notepad.composable.login_register.LoginPage
import com.example.notepad.composable.login_register.RegisterPage
import com.example.notepad.composable.notes.CreateNote
import com.example.notepad.composable.notes.EditNote
import com.example.notepad.composable.notes.NoteDetails
import com.example.notepad.composable.notes.NoteList
import com.example.notepad.composable.notes.NotesViewModel
import com.example.notepad.composable.notes.NotesViewModelFactory
import com.example.notepad.data.AppDb
import com.example.notepad.data.notes_data.NotesRepository
import com.example.notepad.data.user_data.UserRepository
import com.example.notepad.ui.theme.NotepadTheme


@Suppress("DEPRECATION")
class MainActivity : ComponentActivity() {
    private lateinit var notesViewModel: NotesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        super.onCreate(savedInstanceState)

        val notesRepository = NotesRepository(context = applicationContext)
        val userRepository = UserRepository(context = applicationContext)

        AppDb.getInstance(this).noteDao()
        val notesViewModelFactory = NotesViewModelFactory(notesRepository, userRepository)
        notesViewModel = ViewModelProvider(this, notesViewModelFactory)[NotesViewModel::class.java]

        setContent {
            NotepadTheme {
                LoginScreen(notesViewModel)
            }
        }
    }
}

@Composable
fun LoginScreen(notesViewModel: NotesViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login_page",
        builder = {
            composable("login_page") {
                LoginPage(navController = navController, notesViewModel = notesViewModel)
            }

            composable("register_page") {
                RegisterPage(navController = navController)
            }

            composable(
                "notelist_page/{userId}",
                arguments = listOf(navArgument("userId") {
                    type = NavType.IntType
                })
            ) {
                val userId = remember {
                    it.arguments?.getInt("userId")
                }
                if (userId != null) {
                    NoteList(navController = navController, notesViewModel,userId)
                }
            }

            composable("createnote_page") {
                CreateNote(navController = navController, notesViewModel)
            }

            composable(
                "noteDetail/{noteId}",
                arguments = listOf(navArgument("noteId") {
                    type = NavType.IntType
                })
            ) { backStackEntry ->
                backStackEntry.arguments?.getInt("noteId")
                    ?.let { NoteDetails(noteId = it, navController, notesViewModel) }
            }

            composable(
                "editNote/{noteId}",
                arguments = listOf(navArgument("noteId") {
                    type = NavType.IntType
                })
            ) { backStackEntry ->
                backStackEntry.arguments?.getInt("noteId")
                    ?.let { EditNote(noteId = it, navController, notesViewModel) }
            }
        }
    )
}