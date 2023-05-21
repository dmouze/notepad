package com.example.notepad.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.notepad.Constants
import com.example.notepad.data.notes_data.Note
import com.example.notepad.data.notes_data.NoteDAO
import com.example.notepad.data.user_data.User
import com.example.notepad.data.user_data.UserDAO

@Database(entities = [Note::class, User::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDAO
    abstract fun userDao(): UserDAO

    companion object {
        private var db: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (db == null) {
                db = Room.databaseBuilder(context, AppDatabase::class.java, Constants.DATABASE_NAME)
                    .addMigrations(MIGRATION_2_3)
                    .build()
            }
            return db!!
        }

        private val MIGRATION_2_3: Migration = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE IF NOT EXISTS `notes_new` (`id` INTEGER PRIMARY KEY AUTOINCREMENT, `title` TEXT, `content` TEXT, `userId` INTEGER, `dateUpdated` TEXT)")
                database.execSQL("INSERT INTO `notes_new` (`id`, `title`, `content`, `userId`, `dateUpdated`) SELECT `id`, `title`, '', `userId`, `dateUpdated` FROM `notes`")
                database.execSQL("DROP TABLE `notes`")
                database.execSQL("ALTER TABLE `notes_new` RENAME TO `notes`")
            }
        }
    }
}