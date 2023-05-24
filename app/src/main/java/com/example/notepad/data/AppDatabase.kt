package com.example.notepad.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notepad.Constants
import com.example.notepad.data.notes_data.Note
import com.example.notepad.data.notes_data.NoteDAO
import com.example.notepad.data.user_data.User
import com.example.notepad.data.user_data.UserDAO

@Database(entities = [Note::class, User::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDAO
    abstract fun userDao(): UserDAO
}

object AppDb {
    private var db: AppDatabase? = null

    fun getInstance(context: Context): AppDatabase {
        if (db == null) {
            db = Room.databaseBuilder(context, AppDatabase::class.java, Constants.DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }
        return db!!
    }
}