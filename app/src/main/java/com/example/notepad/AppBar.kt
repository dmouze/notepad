package com.example.notepad

import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color


@Composable
fun AppBar(
    title: String,
    onIconClick: (() -> Unit)?,
    icon: @Composable (() -> Unit)?,
    iconState: MutableState<Boolean>
) {
    TopAppBar(
        title = { Text(title) },
        backgroundColor = Color.White,
        actions = {
            IconButton(
                onClick = {
                    onIconClick?.invoke()
                },
                content = {
                    if (iconState.value){
                        icon?.invoke()
                    }
                }

            )
        }
    )
}