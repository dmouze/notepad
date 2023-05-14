package com.example.notepad.composable.note_main_page

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.notepad.R
import com.example.notepad.ui.theme.primaryColor

@Composable
fun NotesPage(
    navController: NavController
) {

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {

            Text(
                text = "Welcome!",
                modifier = Modifier
                    .padding(15.dp)
                    .align(Alignment.TopStart),
                fontSize = 30.sp,
                style = TextStyle(
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 2.sp
                )
            )

        FloatingActionButton(
            onClick = {
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(30.dp)
                .size(60.dp),
            containerColor = primaryColor,
            shape = CircleShape
        ) {
            Icon(
                painter = painterResource(id = R.drawable.plus),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(30.dp)
            )
        }
    }
}