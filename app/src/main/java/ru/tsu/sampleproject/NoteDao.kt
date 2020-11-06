package ru.tsu.sampleproject

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NoteDao{
    @Insert
    fun addNote(note: Note)

    @Delete
    fun deleteUser(note: Note)

    @Query("SELECT * FROM note_table")
    fun getAll(): List<Note>

    @Query("SELECT * FROM note_table WHERE theme LIKE :theme")
    fun getAllNotesOfCategory(theme: String): List<Note>
}