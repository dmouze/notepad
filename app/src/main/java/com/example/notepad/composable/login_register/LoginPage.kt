package com.example.notepad.composable.login_register

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
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
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
import com.example.notepad.R
import com.example.notepad.composable.notes.NotesViewModel
import com.example.notepad.ui.theme.primaryColor
import kotlinx.coroutines.runBlocking

@Composable
fun LoginPage(
    navController: NavController,
    userViewModel: UserViewModel = viewModel(),
    notesViewModel: NotesViewModel = viewModel()
) {
    val loginState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    val passwordVisibility = remember { mutableStateOf(false) }
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.BottomCenter
    ) {
        Image(
            painter = painterResource(id = R.drawable.login), contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
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
                OutlinedTextField(
                    value = loginState.value,
                    onValueChange = {
                        loginState.value = it
                        userViewModel.logout()
                    },
                    label = { Text(text = "Login name") },
                    placeholder = { Text(text = "Login name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(0.88f),
                    colors = TextFieldDefaults.outlinedTextFieldColors(textColor = Color.Black)
                )
                OutlinedTextField(
                    value = passwordState.value,
                    onValueChange = {
                        passwordState.value = it
                        userViewModel.logout()
                    },
                    trailingIcon = {
                        IconButton(onClick = {
                            passwordVisibility.value = !passwordVisibility.value
                        }) {
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
                        userViewModel.logout()
                        val userExists = runBlocking {
                            userViewModel.checkIfUserExists(loginState.value)
                        }
                        if (userExists) {
                            val validPassword = runBlocking {
                                userViewModel.checkPassword(
                                    loginState.value,
                                    passwordState.value
                                )
                            }
                            if (passwordState.value.isNotEmpty() && validPassword) {
                                runBlocking {
                                    userViewModel.login(
                                        loginState.value,
                                        passwordState.value
                                    )
                                }

                                userViewModel.currentUser.value!!.id?.let {
                                    notesViewModel.reloadNotes(
                                        it
                                    )
                                }

                                navController.navigate("notelist_page/${userViewModel.currentUser.value!!.id}") {
                                    launchSingleTop = true
                                }
                            } else {
                                Toast.makeText(
                                    context,
                                    "Incorrect login or password.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
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
                    Text(text = "Sign In", fontSize = 20.sp, color = Color.White)
                }

                Spacer(modifier = Modifier.padding(20.dp))

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


