package com.example.notepad

import android.content.Context
import com.example.notepad.data.Note
import com.example.notepad.data.NoteDAO
import com.example.notepad.data.NoteDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class Repository(context: Context) : NoteDAO{

    private val dao = NoteDb.getInstance(context).noteDao()
    override suspend fun insertAll(notes: List<Note>) = withContext(Dispatchers.IO) {
        dao.insertAll(notes)
    }

    override suspend fun delete(notes: List<Note>) = withContext(Dispatchers.IO){
        dao.delete(notes)
    }

    override suspend fun update(notes: Note) = withContext(Dispatchers.IO){
        dao.update(notes)
    }

    override fun getAll(): Flow<List<Note>> {
        return dao.getAll()
    }

    override suspend fun dropDatabase() = withContext(Dispatchers.IO) {
        dao.dropDatabase()
    }

}