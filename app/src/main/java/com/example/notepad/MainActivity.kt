package com.example.notepad

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import com.example.notepad.composable.LoginPage
import com.example.notepad.ui.theme.NotepadTheme


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
        LoginPage()
    }
}



