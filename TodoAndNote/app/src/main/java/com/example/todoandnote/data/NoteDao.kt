package com.example.todoandnote.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NoteDao {

    @Insert
    fun insert(note: Note)

    @Query("SELECT * FROM note")
    fun getAll() : List<Note>

    @Delete
    fun delete(note: Note)
}