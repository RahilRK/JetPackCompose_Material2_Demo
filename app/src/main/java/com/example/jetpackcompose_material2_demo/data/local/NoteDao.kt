package com.example.jetpackcompose_material2_demo.data.local

import androidx.room.*
import com.example.jetpackcompose_material2_demo.data.model.NoteModel
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("select * from notes")
    fun getAllNotes(): Flow<List<NoteModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveNote(noteModel: NoteModel) : Long

    @Query("DELETE FROM notes where id =:id")
    suspend fun deleteNote(id: Int): Int

    @Query("select * from notes where id= :id")
    fun getNoteDetail(id: Int): Flow<NoteModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateNote(noteModel: NoteModel) : Long
}