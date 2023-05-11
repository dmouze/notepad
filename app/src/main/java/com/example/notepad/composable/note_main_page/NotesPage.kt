package com.example.notepad.composable.note_main_page

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.notepad.composable.login_register.LoginRegisterViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NotesPage(navController: NavController, loginRegisterViewModel: LoginRegisterViewModel = viewModel()) {
    val users = loginRegisterViewModel.getAllUsers().collectAsState(initial = emptyList()).value
    Box{

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            if (users.isEmpty()) {
                Text(text = "Brak użytkowników", modifier = Modifier.align(Alignment.CenterHorizontally))
            } else {
                LazyColumn {
                    items(users) { user ->
                        Text(text = user.nameValue)
                        Text(text = user.loginValue)
                        Text(text = user.id.toString())
                        Divider()
                    }
                }
            }
        }
    }
}
