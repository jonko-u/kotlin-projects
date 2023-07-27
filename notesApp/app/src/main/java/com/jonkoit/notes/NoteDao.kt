package com.jonkoit.notes

import androidx.room.*

@Dao
interface NoteDao {
    @Insert
    fun insert(note: Note)

    @Update
    fun update(note: Note)

    @Delete
    fun delete(note: Note)

    @Query("SELECT * FROM notes")
    fun getAllNotes(): List<Note>

    @Query("SELECT * FROM notes WHERE id = :noteId")
    fun getNoteById(noteId: Long): Note?

    @Query("SELECT * FROM notes WHERE tittle LIKE '%' || :title || '%'")
    fun getNotesByTitle(title: String): List<Note>
}