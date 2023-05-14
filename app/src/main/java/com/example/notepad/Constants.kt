package com.example.notepad

import com.example.notepad.data.notes_data.Note

object Constants {
    const val NOTES_TABLE_NAME = "notes"
    const val USERS_TABLE_NAME = "users"
    const val DATABASE_NAME = "appDatabase"
    const val NAVIGATION_NOTES_LIST = "notesList"
    const val NAVIGATION_NOTES_CREATE = "notesCreated"
    const val NAVIGATION_NOTE_DETAIL = "noteDetail/{noteId}"
    const val NAVIGATION_NOTE_EDIT = "noteEdit/{noteId}"
    const val NAVIGATION_NOTE_ID_Argument = "noteId"

    fun noteDetailNavigation(noteId: Int) = "noteDetail/$noteId"
    fun noteEditNavigation(noteId: Int) = "noteEdit/$noteId"


    fun List<Note>?.orPlaceHolderList(): List<Note> {
        fun placeHolderList(): List<Note> {
            return listOf(
                Note(
                    id = 0,
                    title = "There are no notes",
                    note = "Please create a new note",
                    dateUpdated = ""
                )
            )
        }
        return if (!this.isNullOrEmpty()) {
            this
        } else placeHolderList()
    }

    val noteDetailPlaceHolder =
        Note(note = "Cannot find note details", id = 0, title = "Cannot find note details")
}
