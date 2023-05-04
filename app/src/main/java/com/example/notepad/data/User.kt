package com.example.notepad.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(tableName = Constants.USERS_TABLE_NAME, indices = [Index(value = ["id"], unique = true)])
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "nameValue") val nameValue: String,
    @ColumnInfo(name = "loginValue") val loginValue: String,
    @ColumnInfo(name = "passwordValue") val passwordValue: String
)