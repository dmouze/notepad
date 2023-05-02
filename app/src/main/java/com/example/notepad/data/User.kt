package com.example.notepad.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users_table")
data class User(
    @PrimaryKey(autoGenerate = true) val uid: Int=0,
    val nameValue: String,
    val loginValue: String,
    val passwordValue: String

)