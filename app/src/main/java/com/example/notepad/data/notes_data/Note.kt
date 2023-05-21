package com.example.notepad.data.notes_data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.notepad.Constants
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Entity(tableName = Constants.NOTES_TABLE_NAME, indices = [Index(value = ["id"], unique = true)])
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "userId") val userId: Int? = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "note") val note: String,
    @ColumnInfo(name = "dateUpdated") val dateUpdated: String = getDateCreated()
    )

fun getDateCreated(): String {
    return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))
}

fun Note.getDay(): String {
    if (this.dateUpdated.isEmpty()) return ""
    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
    return LocalDateTime.parse(this.dateUpdated, formatter).toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
}