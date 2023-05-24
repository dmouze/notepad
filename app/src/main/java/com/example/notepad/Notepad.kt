package com.example.notepad

import android.app.Application
import com.example.notepad.data.AppDatabase
import com.example.notepad.data.notes_data.NoteDAO

class Notepad : Application() {

    private lateinit var db: AppDatabase

    companion object {
        private lateinit var instance: Notepad

        fun getDao(): NoteDAO {
            if (!::instance.isInitialized) {
                throw IllegalStateException("Instance has not been initialized.")
            }
            return instance.db.noteDao()
        }
    }

    init {
        instance = this
    }
}