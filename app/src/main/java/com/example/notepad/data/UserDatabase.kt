package com.example.notepad.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDAO

}

object UserDb{
    private var db: UserDatabase? = null

    fun getInstance(context: Context): UserDatabase {
        if (db == null){
            db = Room.databaseBuilder(
                context,
                UserDatabase::class.java,
                "user-database"
            ).build()
        }
        return db!!
    }
}