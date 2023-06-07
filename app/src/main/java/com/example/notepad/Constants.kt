package com.example.notepad

import com.example.notepad.data.notes_data.Note

object Constants {
    const val NOTES_TABLE_NAME = "notes"
    const val USERS_TABLE_NAME = "users"
    const val DATABASE_NAME = "appDatabase"

    fun noteDetailNavigation(noteId: Int) = "noteDetail/$noteId"
    fun noteEditNavigation(noteId: Int) = "editNote/$noteId"

    val noteDetailPlaceHolder =
        Note(note = "Cannot find note details", id = 0, title = "Cannot find note details")
}
