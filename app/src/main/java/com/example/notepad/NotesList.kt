//package com.example.notepad
//
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.activity.viewModels
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Surface
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.collectAsState
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.text.font.FontStyle
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.example.notepad.data.Note
//import com.example.notepad.ui.theme.NotepadTheme
//
//class NotesList: ComponentActivity() {
//    private val mainVm by viewModels<MainViewModel>()
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            NotepadTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    val notes = mainVm.get().collectAsState(initial = emptyList())
//                    NoteList(note = notes.value)
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun NoteList(note: List<Note>){
//    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
//        NotesLazyColumn(note)
//    }
//}
//
//@Composable
//fun NotesLazyColumn(note: List<Note>) {
//    LazyColumn() {
//        items(items = note, key = { it.id }){ note ->
//            NoteRow(note)
//        }
//    }
//}
//
//@Composable
//fun NoteRow(note: Note) {
//    Surface(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(1.dp),
//        shape = RoundedCornerShape(10.dp),
//        shadowElevation = 1.dp
//    ) {
//        Row(modifier = Modifier.padding(horizontal = 16.dp),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            Text(text = note.title, fontStyle = FontStyle.Italic, fontSize = 18.sp)
//            Text(text = note.note)
//
//        }
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun NoteRowPreview(){
//    NoteRow(note = note1)
//}