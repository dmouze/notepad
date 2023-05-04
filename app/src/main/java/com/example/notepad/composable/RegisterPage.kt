package com.example.notepad.composable

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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.notepad.R
import com.example.notepad.UsersViewModel
import com.example.notepad.ui.theme.primaryColor
import com.example.notepad.ui.theme.whiteBackground


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterPage(navController: NavController) {

    val nameValue = remember { mutableStateOf("") }
    val loginValue = remember { mutableStateOf("") }
    val passwordValue = remember { mutableStateOf("") }
    val confirmPasswordValue = remember { mutableStateOf("") }
    val passwordVisibility = remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.TopCenter
        ) {

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "REGISTER",
                    fontSize = 60.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "PAGE",
                    fontSize = 60.sp,
                    fontWeight = FontWeight.Bold,
                )

            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                    .background(whiteBackground)
                    .padding(10.dp),
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
                            text = "Sign Up",
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
                                onValueChange = { loginValue.value = it },
                                label = {
                                    Text(
                                        text = "Login"
                                    )
                                },
                                placeholder = { Text(text = "Login") },
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth(0.8f),
                                colors = TextFieldDefaults.outlinedTextFieldColors(textColor = Color.Black)
                            )

                            OutlinedTextField(
                                value = nameValue.value,
                                onValueChange = { nameValue.value = it },
                                label = { Text(text = "Name") },
                                placeholder = { Text(text = "Name") },
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth(0.8f),
                                colors = TextFieldDefaults.outlinedTextFieldColors(textColor = Color.Black)
                            )

                            OutlinedTextField(
                                value = passwordValue.value,
                                onValueChange = { passwordValue.value = it },
                                label = { Text(text = "Password") },
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
                                onValueChange = { confirmPasswordValue.value = it },
                                label = { Text(text = "Confirm Password") },
                                placeholder = { Text(text = "Confirm Password") },
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth(0.8f),
                                colors = TextFieldDefaults.outlinedTextFieldColors(textColor = Color.Black)
                            )
                            Spacer(modifier = Modifier.padding(10.dp))
                            Button(
                                onClick = {

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
}


