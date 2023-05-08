package com.example.notepad

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.notepad.composable.LoginPage
import com.example.notepad.composable.NotesPage
import com.example.notepad.composable.RegisterPage
import com.example.notepad.ui.theme.NotepadTheme


@Suppress("DEPRECATION")
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        super.onCreate(savedInstanceState)
        setContent {
            NotepadTheme {
                Login()
            }
        }

    }

    @Composable
    fun Login() {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = "login_page", builder = {
                composable("login_page", content = {LoginPage(navController = navController)
                })
                composable(
                    "register_page",
                    content = { RegisterPage(navController = navController) })
                composable("notes_page", content = { NotesPage(navController = navController) })
            })
    }
}






