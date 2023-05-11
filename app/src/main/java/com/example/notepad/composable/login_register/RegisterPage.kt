package com.example.notepad.composable.login_register

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.notepad.R
import com.example.notepad.ui.theme.primaryColor
import kotlinx.coroutines.runBlocking


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterPage(
    navController: NavController,
    loginRegisterViewModel: LoginRegisterViewModel = viewModel()
) {

    val nameValue = remember { mutableStateOf("") }
    val loginValue = remember { mutableStateOf("") }
    val passwordValue = remember { mutableStateOf("") }
    val confirmPasswordValue = remember { mutableStateOf("") }
    val passwordVisibility = remember { mutableStateOf(false) }
    val confirmPasswordVisibility = remember { mutableStateOf(false) }

    val nameErrorState = remember { mutableStateOf(false) }
    val loginErrorState = remember { mutableStateOf(false) }
    val passwordErrorState = remember { mutableStateOf(false) }
    val confirmPasswordErrorState = remember { mutableStateOf(false) }

    val context = LocalContext.current

    var userExist: Boolean
    var allFields = true





    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.TopCenter
        ) {

            Image(
                painter = painterResource(id = R.drawable.register), contentDescription = null,
                modifier = Modifier
                    .align(Alignment.TopCenter),
                contentScale = ContentScale.Crop
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.76f)
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Text(
                        text = "Welcome!",
                        fontSize = 30.sp,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 2.sp
                        )

                    )
                    Spacer(modifier = Modifier.padding(20.dp))
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        OutlinedTextField(
                            value = loginValue.value,
                            onValueChange = {

                                if (loginErrorState.value) {
                                    loginErrorState.value = false
                                }
                                loginValue.value = it
                                runBlocking {
                                    userExist =
                                        loginRegisterViewModel.checkIfUserExists(loginValue.value)
                                }
                            },

                            isError = loginErrorState.value,
                            label = {

                                if (loginErrorState.value) {
                                    Text(text = "Required", color = Color.Red)
                                } else {
                                    Text(text = "Login")
                                }
                            },
                            placeholder = { Text(text = "Login") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(0.8f),
                            colors = TextFieldDefaults.outlinedTextFieldColors(textColor = Color.Black)
                        )

                        OutlinedTextField(
                            value = nameValue.value,
                            onValueChange = {
                                if (nameErrorState.value) {
                                    nameErrorState.value = false
                                }
                                nameValue.value = it
                            },

                            isError = nameErrorState.value,
                            label = {

                                if (nameErrorState.value) {
                                    Text(text = "Required", color = Color.Red)
                                } else {
                                    Text(text = "Name")
                                }
                            },
                            placeholder = { Text(text = "Name") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(0.8f),
                            colors = TextFieldDefaults.outlinedTextFieldColors(textColor = Color.Black)
                        )

                        OutlinedTextField(
                            value = passwordValue.value,
                            onValueChange = {
                                if (passwordErrorState.value) {
                                    passwordErrorState.value = false
                                }
                                passwordValue.value = it
                            },
                            isError = passwordErrorState.value,
                            label = {
                                if (passwordErrorState.value) {
                                    Text(text = "Required", color = Color.Red)
                                } else {
                                    Text(text = "Password")
                                }
                            },
                            placeholder = { Text(text = "Password") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(0.8f),
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
                            visualTransformation = if (passwordVisibility.value) VisualTransformation.None
                            else PasswordVisualTransformation(),
                            colors = TextFieldDefaults.outlinedTextFieldColors(textColor = Color.Black)
                        )

                        OutlinedTextField(
                            value = confirmPasswordValue.value,
                            onValueChange = {
                                if (confirmPasswordErrorState.value) {
                                    confirmPasswordErrorState.value = false
                                }
                                confirmPasswordValue.value = it
                            },
                            isError = confirmPasswordErrorState.value,
                            label = {
                                if (confirmPasswordErrorState.value) {
                                    Text(text = "Required", color = Color.Red)
                                } else {
                                    Text(text = "Password")
                                }
                            },
                            placeholder = { Text(text = "Password") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(0.8f),
                            trailingIcon = {
                                IconButton(onClick = {
                                    confirmPasswordVisibility.value =
                                        !confirmPasswordVisibility.value
                                }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.password_hide),
                                        contentDescription = null,
                                        tint = if (confirmPasswordVisibility.value) primaryColor else Color.Gray
                                    )
                                }
                            },
                            visualTransformation = if (confirmPasswordVisibility.value) VisualTransformation.None
                            else PasswordVisualTransformation(),
                            colors = TextFieldDefaults.outlinedTextFieldColors(textColor = Color.Black)
                        )
                        Spacer(modifier = Modifier.padding(10.dp))
                        Button(
                            onClick = {
                                runBlocking {
                                    userExist =
                                        loginRegisterViewModel.checkIfUserExists(loginValue.value)
                                }

                                if (!loginErrorState.value && !nameErrorState.value && !passwordErrorState.value && !confirmPasswordErrorState.value){
                                    allFields = true
                                }


                                if (loginValue.value.isEmpty()) {
                                    loginErrorState.value = true
                                    allFields = false
                                }

                                if (passwordValue.value.isEmpty()) {
                                    passwordErrorState.value = true
                                    allFields = false
                                }

                                if (nameValue.value.isEmpty()) {
                                    nameErrorState.value = true
                                    allFields = false
                                }

                                if (confirmPasswordValue.value.isEmpty()) {
                                    confirmPasswordErrorState.value = true
                                    allFields = false
                                }

                                if (!allFields){
                                    Toast.makeText(
                                        context,
                                        "Please fill all fields",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }

                                if (passwordValue.value != confirmPasswordValue.value) {
                                    Toast.makeText(
                                        context,
                                        "Passwords aren't matching, please try to type them again",
                                        Toast.LENGTH_LONG
                                    ).show()
                                } else if (userExist) {
                                    Toast.makeText(
                                        context,
                                        "User with login '${loginValue.value}' already exists! Please choose a different login.",
                                        Toast.LENGTH_LONG
                                    ).show()
                                } else {
                                    if (allFields) {
                                        loginRegisterViewModel.registerUser(
                                            nameValue.value,
                                            loginValue.value,
                                            passwordValue.value,
                                            confirmPasswordValue.value
                                        )
                                        Toast.makeText(
                                            context,
                                            "User '${nameValue.value}' registered successfully!",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .height(50.dp)
                        ) {
                            Text(text = "Sign Up", fontSize = 20.sp)
                        }
                    }
                    Spacer(modifier = Modifier.padding(20.dp))
                    Text(
                        text = "Login Instead",
                        modifier = Modifier.clickable(onClick = {
                            navController.navigate("login_page") {
                                launchSingleTop = true
                            }
                        })
                    )
                    Spacer(modifier = Modifier.padding(20.dp))

                }
            }
        }
    }

}



