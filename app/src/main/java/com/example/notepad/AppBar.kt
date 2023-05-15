package com.example.notepad

import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState


@Composable
fun AppBar(
    title: String,
    onIconClick: (() -> Unit)?,
    icon: @Composable (() -> Unit)?,
    iconState: MutableState<Boolean>
) {
    TopAppBar(
        title = { Text(title) },
        backgroundColor = MaterialTheme.colors.primary,
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