package com.example.notepad.composable

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.notepad.MainViewModel
import com.example.notepad.R
import com.example.notepad.ui.theme.primaryColor
import kotlinx.coroutines.runBlocking

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPage(
    navController: NavController,
    mainViewModel: MainViewModel = viewModel()
) {
    // Utwórz stan dla pola login i hasła
    val loginState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    val passwordVisibility = remember { mutableStateOf(false) }
    val context = LocalContext.current

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Image(
            painter = painterResource(id = R.drawable.login), contentDescription = null,
            modifier = Modifier.fillMaxWidth()
                .align(Alignment.TopCenter),
            contentScale = ContentScale.Crop
        )
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.45f)
                .background(Color.White)
        ) {
            item {
                // Dodaj pola tekstowe dla loginu i hasła
                OutlinedTextField(
                    value = loginState.value,
                    onValueChange = { loginState.value = it },
                    label = { Text(text = "Login name") },
                    placeholder = { Text(text = "Login name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(0.88f),
                    colors = TextFieldDefaults.outlinedTextFieldColors(textColor = Color.Black)
                )
                OutlinedTextField(
                    value = passwordState.value,
                    onValueChange = { passwordState.value = it },
                    trailingIcon = {
                        IconButton(onClick = { }) {
                            Icon(
                                painter = painterResource(id = R.drawable.password_hide),
                                contentDescription = null,
                                tint = if (passwordVisibility.value) primaryColor else Color.Gray
                            )
                        }
                    },
                    label = { Text("Password") },
                    placeholder = { Text(text = "Password") },
                    singleLine = true,
                    visualTransformation = if (passwordVisibility.value) VisualTransformation.None
                    else PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(0.88f),
                    colors = TextFieldDefaults.outlinedTextFieldColors(textColor = Color.Black)
                )
                Spacer(modifier = Modifier.padding(10.dp))

                Button(
                    onClick = {
                        // Sprawdź, czy użytkownik istnieje
                        val userExists = runBlocking {
                            mainViewModel.checkIfUserExists(loginState.value)
                        }
                        if (userExists) {
                            val validPassword = runBlocking {
                                mainViewModel.checkPassword(loginState.value, passwordState.value)
                            }
                            // Sprawdź, czy hasło zostało uzupełnione i czy zgadza się z użytkownikiem
                            if (passwordState.value.isNotEmpty() && validPassword) {
                                // Wywołaj funkcję logowania z ViewModel
                                runBlocking {
                                    mainViewModel.login(loginState.value, passwordState.value)
                                }
                                navController.navigate("notes_page") {
                                    launchSingleTop = true
                                }
                            } else {
                                // Wyświetl informację o błędzie
                                Toast.makeText(
                                    context,
                                    "Incorrect login or password.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            // Wyświetl informację o błędzie
                            Toast.makeText(
                                context,
                                "User doesn't exist.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .height(50.dp)
                ) {
                    Text(text = "Sign In", fontSize = 20.sp)
                }

                Spacer(modifier = Modifier.padding(20.dp))

                // Dodaj odnośnik do strony rejestracji
                Text(
                    text = "Create an Account",
                    modifier = Modifier.clickable(onClick = {
                        navController.navigate("register_page") {
                            launchSingleTop = true
                        }
                    })
                )

                Spacer(modifier = Modifier.padding(20.dp))
            }
        }
    }
}
